package com.devbank.user.management.rest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/login",
                                "/api/users/register",
                                "/api/users/password-reset/request",
                                "/api/users/password-reset/confirm"
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
                        .anyRequest().authenticated() // Diğer istekler için doğrulama iste
                );
        System.out.println("Security Config intilized");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
