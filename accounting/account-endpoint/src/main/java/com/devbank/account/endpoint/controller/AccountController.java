package com.devbank.account.endpoint.controller;

import com.devbank.account.api.DTO.AccountDTO;
import com.devbank.account.impl.mongo.document.AccountDocument;
import com.devbank.account.impl.mongo.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public AccountDocument createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('BUSINESS_USER')")
    public AccountDocument getAccountById(@PathVariable String accountId, Principal principal) {
        return accountService.getAccountById(accountId, principal.getName());
    }

    @GetMapping("/number/{accountNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('BUSINESS_USER')")
    public AccountDocument getAccountByNumber(@PathVariable String accountNumber, Principal principal) {
        return accountService.getAccountByNumber(accountNumber, principal.getName());
    }


    @GetMapping("/{accountId}/balance")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('BUSINESS_USER')")
    public double getBalance(@PathVariable String accountId, Principal principal) {
        return accountService.getBalance(accountId, principal.getName());
    }

    @PutMapping("/{accountId}")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public AccountDocument updateAccount(@PathVariable String accountId, @RequestBody AccountDTO accountDTO, Principal principal) {
        return accountService.updateAccount(accountId, accountDTO, principal.getName());
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
    }
}
