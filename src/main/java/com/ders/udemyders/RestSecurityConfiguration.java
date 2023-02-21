package com.ders.udemyders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Order(value = 1)
@Configuration
@NoArgsConstructor
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {

    @Bean
    public SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception{

        http.antMatcher("/rest/**");
        http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
        http.csrf().disable();
        http.httpBasic();


        return http.build();
    }

}
