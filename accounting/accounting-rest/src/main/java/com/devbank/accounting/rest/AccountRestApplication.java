package com.devbank.accounting.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.devbank")
public class AccountRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountRestApplication.class, args);
    }
}