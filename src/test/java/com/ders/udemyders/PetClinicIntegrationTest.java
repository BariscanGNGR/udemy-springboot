package com.ders.udemyders;


import com.ders.udemyders.model.Owner;
import com.ders.udemyders.model.Vet;
import com.ders.udemyders.service.PetClinicService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootTest(properties = {"spring.profiles.active=dev"})
public class PetClinicIntegrationTest {
    @Autowired
    private PetClinicService petClinicService;

    @BeforeAll
    public static void setup(){
        TestingAuthenticationToken auth = new TestingAuthenticationToken("user1","secret","ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void testFindOwners(){
        List<Owner> owners = petClinicService.findOwners();
        MatcherAssert.assertThat(owners.size(), Matchers.equalTo(10));
    }

    @Test
    public void testFindVets(){
        List<Vet> vets = petClinicService.findVets();
        MatcherAssert.assertThat(vets.size(), Matchers.equalTo(3));
    }

    @AfterAll
    public static void tearDown(){
        SecurityContextHolder.clearContext();
    }
}
