package com.devbank.accounting.rest.config;

import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
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
            // Vadesiz hesap
            AccountDocument savingsAccount = new AccountDocument(
                    "1L",
                    "100L",
                    AccountType.SAVINGS,
                    5000.0, // Başlangıç bakiyesi
                    "TR1234567891234567",
                    new Date(),
                    null,  // Faiz oranı yok
                    null   // Vade tarihi yok
            );
            accountRepository.save(savingsAccount);

            // Vadeli hesap
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 12); // 12 ay vadeli hesap

            AccountDocument fixedAccount = new AccountDocument(
                    "2L",
                    "101L",
                    AccountType.CURRENT,
                    10000.0, // Başlangıç bakiyesi
                    "TR9876543219876543",
                    new Date(),
                    2.0,    // Faiz oranı %2
                    calendar.getTime() // Vade bitiş tarihi
            );
            accountRepository.save(fixedAccount);

            System.out.println("Accounting initial data loaded with two accounts.");
        }
    }
}
