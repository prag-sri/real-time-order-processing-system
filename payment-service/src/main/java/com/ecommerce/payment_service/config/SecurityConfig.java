package com.ecommerce.payment_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.
                csrf(CsrfConfigurer::disable)      // Disbale CSRF (Cross-Site Request Forgery)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()   // Allow all requests
                );
        return http.build();
    }
}
