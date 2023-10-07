package com.hasan.foraty.myblogapplication.config;

 import com.hasan.foraty.myblogapplication.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder getPasswordEncoder(){
      return new BCryptPasswordEncoder();
    }
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
      return configuration.getAuthenticationManager();
  }
    

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter,
        @Qualifier("customAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler) throws Exception {
        httpSecurity.exceptionHandling((ex)->{
            ex.accessDeniedHandler(accessDeniedHandler);
            ex.authenticationEntryPoint(authenticationEntryPoint);
        });
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
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
        ;

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
