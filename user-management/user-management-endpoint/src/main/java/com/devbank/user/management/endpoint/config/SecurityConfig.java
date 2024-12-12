package com.devbank.user.management.endpoint.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF'yi devre dışı bırak
                .authorizeHttpRequests(auth -> auth
                        // Hesap Yönetimi
                        .requestMatchers(HttpMethod.POST, "/api/accounts").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/accounts/{accountId}").hasAnyRole("USER", "EMPLOYEE", "ADMIN", "BUSINESS_USER")
                        .requestMatchers(HttpMethod.GET, "/api/accounts/{accountId}/balance").hasAnyRole("USER", "EMPLOYEE", "ADMIN", "BUSINESS_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/accounts/{accountId}").hasAnyRole("USER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/accounts/{accountId}").hasRole("ADMIN")

                        // Transfer yönetimi
                        .requestMatchers(HttpMethod.POST, "/api/transactions/transfer").hasAnyRole("USER", "EMPLOYEE", "ADMIN", "BUSINESS_USER")
                        .requestMatchers(HttpMethod.POST, "/api/transactions/deposit/**").hasAnyRole("USER", "EMPLOYEE", "ADMIN", "BUSINESS_USER")
                        .requestMatchers(HttpMethod.POST, "/api/transactions/withdraw/**").hasAnyRole("USER", "EMPLOYEE", "ADMIN", "BUSINESS_USER")
                        .anyRequest().authenticated()

                        // Genel Kurallar
                        .anyRequest().authenticated()
                );


        return http.build();
    }
}
