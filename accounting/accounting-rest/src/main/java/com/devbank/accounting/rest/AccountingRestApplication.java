package com.devbank.accounting.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.devbank")
@EnableMongoRepositories(basePackages = "com.devbank.accounting.impl.mongo.repository")
public class AccountingRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountingRestApplication.class, args);
        System.out.println("Accounting REST Application is running!");
    }
}