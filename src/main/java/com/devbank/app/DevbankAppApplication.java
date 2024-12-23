package com.devbank.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.devbank")
public class DevbankAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevbankAppApplication.class, args);
	}
}