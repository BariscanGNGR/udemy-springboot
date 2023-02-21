package com.ders.udemyders.security;

import com.ders.udemyders.service.PetClinicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

@SpringBootTest(properties = "spring.profiles.active=dev")
public class PetClinicSecurityWithoutAuthTokenTests {
    @Autowired
    private PetClinicService petClinicService;



    @Test
    public void testFindOwners(){
        Exception exception = Assertions.assertThrows(AuthenticationCredentialsNotFoundException.class,() -> {
            petClinicService.findOwners();
        });

        Assertions.assertTrue(exception.getMessage().contains("An Authentication object was not found in the SecurityContext"));
    }

}
