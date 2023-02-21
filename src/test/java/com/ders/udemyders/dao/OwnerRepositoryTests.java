package com.ders.udemyders.dao;

import com.ders.udemyders.model.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ActiveProfiles("dev")
@SpringBootTest
@Transactional
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreateOwner(){
        Owner owner = new Owner();
        owner.setFirstName(null);
        owner.setLastName(null);

        ownerRepository.create(owner);

        entityManager.flush();
    }
}
