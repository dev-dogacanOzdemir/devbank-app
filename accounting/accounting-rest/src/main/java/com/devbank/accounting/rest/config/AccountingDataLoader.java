package com.devbank.accounting.rest.config;

import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AccountingDataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public AccountingDataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        if (accountRepository.count() == 0) {
            AccountDocument account = new AccountDocument(1L, 100L, AccountType.SAVINGS, 1000.0, new Date(), 1.5, null);
            accountRepository.save(account);
            System.out.println("Accounting initial data loaded.");
        }
    }

}
