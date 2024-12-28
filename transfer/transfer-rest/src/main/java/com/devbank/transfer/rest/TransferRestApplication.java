package com.devbank.transfer.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.devbank.accounting","com.devbank.transfer"})
@EnableMongoRepositories(basePackages = {"com.devbank.transfer.impl.mongo.repository","com.devbank.accounting.impl.mongo.repository"})
public class TransferRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferRestApplication.class, args);
        System.out.println("Transfer REST Application is running!");
    }
}
