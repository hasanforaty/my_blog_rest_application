package com.hasan.foraty.myblogapplication.config;

import com.hasan.foraty.myblogapplication.model.Authority;
import com.hasan.foraty.myblogapplication.model.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
                .authorities(Roles.ADMIN.getAuthorities())
                .build();
        UserDetails hasan = User.builder()
                        .username("hasan")
                                .password("{noop}hasanf12345")
                                        .authorities(Roles.USER.getAuthorities())
                                                .build();
        inMemoryUserDetailsManager.createUser(hasan);
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
    }
    

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize-> {
                    authorize.requestMatchers(HttpMethod.GET,"/api/**")
                            .hasAuthority(Authority.GET_FROM_DATABASE.name());
                    authorize.requestMatchers(HttpMethod.PUT,"/api/**")
                            .hasAuthority(Authority.UPDATE_DATABASE.name());
                    authorize.requestMatchers(HttpMethod.POST,"/api/**")
                            .hasAuthority(Authority.ADD_TO_DATABASE.name());
                    authorize.requestMatchers(HttpMethod.DELETE,"/api/**")
                            .hasAuthority(Authority.REMOVE_FROM_DATABASE.name());
                })
                .httpBasic(Customizer.withDefaults())
        ;


        return httpSecurity.build();
    }
}
