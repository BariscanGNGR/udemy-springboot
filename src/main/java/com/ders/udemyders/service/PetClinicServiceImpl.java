package com.ders.udemyders.service;

import com.ders.udemyders.dao.OwnerRepository;
import com.ders.udemyders.dao.PetRepository;
import com.ders.udemyders.dao.jpa.VetRepository;
import com.ders.udemyders.exception.OwnerNotFoundException;
import com.ders.udemyders.exception.VetNotFoundException;
import com.ders.udemyders.model.Owner;
import com.ders.udemyders.model.Vet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Validated
public class PetClinicServiceImpl implements PetClinicService{

    private OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    //private final JavaMailSender javaMailSender;
    private final VetRepository vetRepository;

    @Autowired
    public void setOwnerRepository(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Secured(value = {"ROLE_USER","ROLE_EDITOR"})
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Owner> findOwners(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Owner findOwner(Long id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id);
        if(owner == null) throw new OwnerNotFoundException("Owner not found with id : " +id);
        return ownerRepository.findById(id);
    }

    @Override
    @CacheEvict(cacheNames = "allOwners",allEntries = true)
    public void createOwner(@Valid Owner owner) {
        ownerRepository.create(owner);

//        Tested with FakeSmtp
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("k@s");
//        msg.setTo("m@y");
//        msg.setSubject("Owner Created!");
//        msg.setText("Owner entity with id : "+owner.getId()+ " created successfully.");

//        javaMailSender.send(msg);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.update(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        petRepository.deleteByOwnerId(id);
        ownerRepository.delete(id);
        //if(true) throw new RuntimeException("testing rollback");
    }

    @Override
    public List<Vet> findVets() {
        return vetRepository.findAll();
    }

    @Override
    public Vet findVet(Long id) throws VetNotFoundException {
        return vetRepository.findById(id).orElseThrow(() -> {return new VetNotFoundException("Vet not found by id : "+ id );});
    }


}
