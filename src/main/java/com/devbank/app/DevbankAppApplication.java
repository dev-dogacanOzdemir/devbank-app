package com.devbank.app;

import com.devbank.accounting.rest.AccountingRestApplication;
import com.devbank.currency.gold.rest.CurrencyGoldRestApplication;
import com.devbank.user.management.rest.UserManagementRestApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class DevbankAppApplication {
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(DevbankAppApplication.class)
				.child(UserManagementRestApplication.class)
				.child(AccountingRestApplication.class)
				.child(CurrencyGoldRestApplication.class);
		builder.run(args);
	}
}