package com.ders.udemyders.security;

import com.ders.udemyders.service.PetClinicService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(properties = "spring.profiles.active=dev")
public class PetClinicSecurityWithInvalidAuthTokenTests {
    @Autowired
    private PetClinicService petClinicService;

    @BeforeAll
    public static void setup(){
        TestingAuthenticationToken auth = new TestingAuthenticationToken("user","secret","ROLE_XXX");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterAll
    public static void tearDown(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testFindOwners(){
        Exception exception = Assertions.assertThrows(AccessDeniedException.class,() -> {
            petClinicService.findOwners();
        });
        Assertions.assertTrue(exception.getMessage().contains("Access is denied"));
    }

}
