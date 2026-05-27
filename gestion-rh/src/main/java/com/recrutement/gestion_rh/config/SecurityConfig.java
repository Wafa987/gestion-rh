package com.recrutement.gestion_rh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        // On autorise Swagger
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // On autorise nos API REST
                        .requestMatchers("/api/**").permitAll()
                        // IMPORTANT : On autorise nos pages web Thymeleaf !
                        .requestMatchers("/web/**").permitAll()
                        .requestMatchers("/web/candidat/**").permitAll()
                        .requestMatchers("/web/login").permitAll()
                        .requestMatchers("/web/auth/**").permitAll()
                        // Tout le reste

                        .anyRequest().permitAll() // On laisse tout ouvert pour tes tests tranquilles
                );

        return http.build();
    }
}