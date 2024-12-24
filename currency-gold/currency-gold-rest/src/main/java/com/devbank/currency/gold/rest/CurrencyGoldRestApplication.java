package com.devbank.currency.gold.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.devbank.currency.gold.impl.mongo",
        "com.devbank.currency.gold.api",
        "com.devbank.currency.gold.rest"})
@EnableMongoRepositories(basePackages = "com.devbank.currency.gold.impl.mongo.repository")
public class CurrencyGoldRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyGoldRestApplication.class, args);
        System.out.println("Currency-Gold REST Application is running!");
    }
}