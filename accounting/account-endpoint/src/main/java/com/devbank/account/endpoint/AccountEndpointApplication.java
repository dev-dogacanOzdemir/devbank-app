package com.devbank.account.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.devbank")
@EnableMongoRepositories(basePackages = "com.devbank.account.impl.mongo.repository")
public class AccountEndpointApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountEndpointApplication.class, args);
    }
}