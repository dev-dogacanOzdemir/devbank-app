package com.devbank.currency.gold.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication()
@ComponentScan(basePackages = {
        "com.devbank.currency.gold.impl.mongo",
        "com.devbank.currency.gold.api",
        "com.devbank.currency.gold.rest"})
@EnableMongoRepositories(basePackages = "com.devbank.currency.gold.impl.mongo.repository")
public class CurrencyGoldApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyGoldApplication.class, args);
    }
}