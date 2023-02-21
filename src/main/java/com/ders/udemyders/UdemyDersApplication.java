package com.ders.udemyders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = PetClinicProperties.class)
@ServletComponentScan
@EnableJpaAuditing(auditorAwareRef = "petClinicAuditorAware")
@EnableCaching
public class UdemyDersApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UdemyDersApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UdemyDersApplication.class);
    }
}
