package com.devbank.transaction.service.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.devbank")
@EnableMongoRepositories(basePackages = {"com.devbank.transaction.service.impl.mongo",
        "com.devbank.account.impl.mongo"})
public class TransactionEndpointApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionEndpointApplication.class, args);
    }
}
