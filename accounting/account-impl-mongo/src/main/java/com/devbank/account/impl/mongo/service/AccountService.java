package com.devbank.account.impl.mongo.service;

import com.devbank.account.api.DTO.AccountDTO;
import com.devbank.account.impl.mongo.document.AccountDocument;
import com.devbank.account.impl.mongo.repository.AccountRepository;
import com.devbank.error.management.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDocument createAccount(AccountDTO accountDTO) {
        if (accountRepository.existsByAccountNumber(accountDTO.getAccountNumber())) {
            throw new CustomException(
                    "Account with number " + accountDTO.getAccountNumber() + " already exists.",
                    HttpStatus.CONFLICT
            );
        }

        AccountDocument account = new AccountDocument();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setOwnerUsername(accountDTO.getCustomerName());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(0.0);

        return accountRepository.save(account);
    }

    public AccountDocument getAccountById(String accountId, String username) {
        AccountDocument account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));

        if (!account.getOwnerUsername().equals(username) &&
                !account.getSharedUsernames().contains(username)) {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }

        return account;
    }

    public AccountDocument getAccountByNumber(String accountNumber, String username) {
        AccountDocument account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));

        // Erişim kontrolü
        if (!account.getOwnerUsername().equals(username) &&
                !account.getSharedUsernames().contains(username)) {
            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        }

        return account;
    }

    public AccountDocument getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
    }

    public double getBalance(String accountId, String username) {
        return getAccountById(accountId, username).getBalance();
    }

    public AccountDocument updateAccount(String accountId, AccountDTO accountDTO, String username) {
        AccountDocument account = getAccountById(accountId, username);
        account.setAccountType(accountDTO.getAccountType());
        return accountRepository.save(account);
    }

    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
    }

    public List<AccountDocument> getAccountsByOwner(String username) {
        return accountRepository.findByOwnerUsername(username);
    }

    public List<AccountDocument> getSharedAccounts(String username) {
        return accountRepository.findBySharedUsernamesContaining(username);
    }

    public AccountDocument getAccountByNumberAndOwner(String accountNumber, String username) {
        return accountRepository.findByAccountNumberAndOwnerUsername(accountNumber, username)
                .orElseThrow(() -> new CustomException("Access denied or account not found", HttpStatus.FORBIDDEN));
    }

}
