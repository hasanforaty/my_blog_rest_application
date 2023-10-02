package com.hasan.foraty.myblogapplication.config;

import com.hasan.foraty.myblogapplication.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder getPasswordEncoder(){
      return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$purpvhO0t1C9CdU/k9oS5eeqFvVIJ9OA/hHSNHA5eZQvclnZWrmde")
//                .authorities(Roles.ADMIN.getAuthorities())
//                .build();
//        UserDetails hasan = User.builder()
//                        .username("hasan")
//                                .password("{noop}hasanf12345")
//                                        .authorities(Roles.USER.getAuthorities())
//                                                .build();
//        inMemoryUserDetailsManager.createUser(hasan);
//        inMemoryUserDetailsManager.createUser(admin);
//        return inMemoryUserDetailsManager;
//    }
//
//    @Bean
//    public UserDetailsManager getUserDetailManager(DataSource dataSource){
//      return  new JdbcUserDetailsManager(dataSource);
//    }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
      return configuration.getAuthenticationManager();
  }
    

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint authenticationEntryPoint,
        @Qualifier("customAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize-> {
                    authorize.requestMatchers(HttpMethod.POST,"/api/auth/**")
                            .permitAll();
                    authorize.requestMatchers(HttpMethod.GET,"/api/**")
                            .hasAnyRole("ADMIN","USER");
                    authorize.requestMatchers(HttpMethod.PUT,"/api/**")
                        .hasAnyRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.POST,"/api/**")
                        .hasAnyRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE,"/api/**")
                        .hasAnyRole("ADMIN");
                })
                .httpBasic(Customizer.withDefaults())
        ;
        httpSecurity.exceptionHandling((ex)->{
          ex.accessDeniedHandler(accessDeniedHandler);
          ex.authenticationEntryPoint(authenticationEntryPoint);
        });


        return httpSecurity.build();
    }
}
