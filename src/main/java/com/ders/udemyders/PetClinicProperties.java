package com.ders.udemyders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("ConfigurationProperties")
@Getter
@Setter
@ConfigurationProperties(prefix = "petclinic")
public class PetClinicProperties {
    private boolean displayOwnersPets = false;
}
