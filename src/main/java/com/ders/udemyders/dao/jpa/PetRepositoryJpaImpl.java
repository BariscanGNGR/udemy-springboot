package com.ders.udemyders.dao.jpa;

import com.ders.udemyders.dao.PetRepository;
import com.ders.udemyders.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("petRepository")
@RequiredArgsConstructor
public class PetRepositoryJpaImpl implements PetRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Pet findById(Long id) {
        return entityManager.find(Pet.class,id);
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return entityManager.createQuery("from Pet where owner.id = :owner.id",Pet.class).setParameter("owner.id",ownerId).getResultList();
    }

    @Override
    public void create(Pet pet) {
        entityManager.persist(pet);
    }

    @Override
    public Pet update(Pet pet) {
        return entityManager.merge(pet);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Pet.class,id));
    }

    @Override
    public void deleteByOwnerId(Long ownerId) {
        entityManager.createQuery("delete from Pet where owner.id = :ownerid").setParameter("ownerid",ownerId).executeUpdate();
    }
}
