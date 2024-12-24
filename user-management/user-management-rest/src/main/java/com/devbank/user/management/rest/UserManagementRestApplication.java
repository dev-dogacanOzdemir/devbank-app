package com.devbank.user.management.rest;

import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.devbank")
@EnableMongoRepositories(basePackages = "com.devbank.user.management.impl.mongo.repository")
public class UserManagementRestApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserManagementRestApplication.class, args);
        System.out.println("User Management REST Application is running!");
    }
}