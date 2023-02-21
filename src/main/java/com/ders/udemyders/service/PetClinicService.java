package com.ders.udemyders.service;

import com.ders.udemyders.exception.OwnerNotFoundException;
import com.ders.udemyders.exception.VetNotFoundException;
import com.ders.udemyders.model.Owner;
import com.ders.udemyders.model.Vet;

import javax.validation.Valid;
import java.util.List;

public interface PetClinicService {
    List<Owner> findOwners();
    List<Owner> findOwners(String lastName);
    Owner findOwner(Long id) throws OwnerNotFoundException;
    void createOwner(@Valid Owner owner);
    void updateOwner(Owner owner);
    void deleteOwner(Long id);

    List<Vet> findVets();
    Vet findVet(Long id) throws VetNotFoundException;
}
