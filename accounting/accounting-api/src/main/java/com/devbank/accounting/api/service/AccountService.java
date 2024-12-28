package com.devbank.accounting.api.service;

import com.devbank.accounting.api.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(String accountId, AccountDTO accountDTO);
    AccountDTO getAccountById(String accountId);
    List<AccountDTO> getAccountsByCustomerId(String customerId);
    List<AccountDTO> getAllAccounts();
    void deleteAccount(String accountId);
    void updateAccountBalance(String accountId, Double newBalance);
}
