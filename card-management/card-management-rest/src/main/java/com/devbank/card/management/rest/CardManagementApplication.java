package com.devbank.card.management.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.devbank.card.management.api",
        "com.devbank.card.management.impl.mongo",
        "com.devbank.card.management.rest",
        "com.devbank.accounting"
})
@EnableMongoRepositories(basePackages ={"com.devbank.accounting.impl.mongo.repository","com.devbank.card.management.impl.mongo.repository"})
public class CardManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementApplication.class, args);
        System.out.println("Card Management Service is running on port 2005");
    }
}
