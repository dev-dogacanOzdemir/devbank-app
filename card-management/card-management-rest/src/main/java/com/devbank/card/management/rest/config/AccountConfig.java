package com.devbank.card.management.rest.config;

import com.devbank.accounting.api.service.AccountService;
import com.devbank.accounting.impl.mongo.mapper.AccountMapper;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import com.devbank.accounting.impl.mongo.service.AccountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountService accountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        return new AccountServiceImpl(accountRepository,accountMapper);
    }
}
