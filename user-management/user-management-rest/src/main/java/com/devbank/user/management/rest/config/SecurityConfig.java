package com.devbank.user.management.rest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))// CSRF korumasını devre dışı bırak
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/password-reset/request",
                                "/api/users/password-reset/confirm",
                                "/api/users/validate/**"
                        ).permitAll() // Herkese açık yollar

                        .requestMatchers(
                                "/api/users/**"
                        ).hasAnyAuthority("ADMIN", "CUSTOMER")
                        .requestMatchers(
                                "/api/users/{tcNumber}",
                                "/api/transfers/account/{accountId}",
                                "/api/transfers",
                                "/api/transfers/scheduled"
                        ).hasAnyAuthority("USER","ADMIN")
                        .requestMatchers(
                                "/api/transfers/{transferId}/status"
                        ).hasAnyAuthority("ADMIN")
                        .requestMatchers("/api/cards/create", "/api/cards/update-status", "/api/cards/update-limit").hasAuthority("ADMIN")
                        .requestMatchers("/api/cards/get/{cardId}", "/api/cards/user/{userId}", "/api/cards/{cardId}/transactions").hasAnyAuthority("CUSTOMER", "ADMIN")
                        .anyRequest().authenticated() // Diğer istekler için doğrulama iste
                );
        System.out.println("Security Config intilized");
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Frontend URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
