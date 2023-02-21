package com.ders.udemyders.dao;

import com.ders.udemyders.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PetRepository {
    Pet findById(Long id);
    List<Pet> findByOwnerId(Long ownerId);
    void create(Pet pet);
    Pet update(Pet pet);
    void delete(Long id);
    void deleteByOwnerId(Long ownerId);
}
