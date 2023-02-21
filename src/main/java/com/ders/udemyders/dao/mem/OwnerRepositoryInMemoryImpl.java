package com.ders.udemyders.dao.mem;

import com.ders.udemyders.dao.OwnerRepository;
import com.ders.udemyders.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {

    private Map<Long,Owner> ownerMap = new HashMap<>();

    public OwnerRepositoryInMemoryImpl(){
        ownerMap.put(1L,Owner.builder()
               //.id(1L)
               .firstName("test1")
               .lastName("test1")
               .build());
        ownerMap.put(2L,Owner.builder()
                //.id(2L)
                .firstName("test2")
                .lastName("test2")
                .build());
        ownerMap.put(3L,Owner.builder()
                //.id(3L)
                .firstName("test3")
                .lastName("test2")
                .build());
        ownerMap.put(4L,Owner.builder()
                //.id(4L)
                .firstName("test4")
                .lastName("test4")
                .build());
    }

    @Override
    public List<Owner> findAll() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public Owner findById(Long id) {
        return ownerMap.get(id);
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        return ownerMap.values().stream().filter(obj->obj.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    @Override
    public void create(Owner owner) {
        owner.setId(new Date().getTime());
        ownerMap.put(owner.getId(),owner);
    }

    @Override
    public Owner update(Owner owner) {
        ownerMap.replace(owner.getId(), owner);
        return owner;
    }

    @Override
    public void delete(Long id) {
        ownerMap.remove(id);
    }
}
