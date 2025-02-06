package com.devbank.loan.management.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.devbank")
@EnableMongoRepositories(basePackages ={"com.devbank.accounting.impl.mongo.repository","com.devbank.loan.management.impl.mongo.repository"} )
public class LoanManagementRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanManagementRestApplication.class, args);
        System.out.println("Loan Management REST Application is running on port 2006!");
    }
}
