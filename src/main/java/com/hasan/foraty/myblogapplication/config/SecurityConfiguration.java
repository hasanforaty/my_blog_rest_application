package com.hasan.foraty.myblogapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$purpvhO0t1C9CdU/k9oS5eeqFvVIJ9OA/hHSNHA5eZQvclnZWrmde")
                .build();
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize-> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
        ;


        return httpSecurity.build();
    }
}
