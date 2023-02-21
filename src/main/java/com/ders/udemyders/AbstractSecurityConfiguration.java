package com.ders.udemyders;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.sql.DataSource;

public abstract class AbstractSecurityConfiguration {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource);
    }
}
