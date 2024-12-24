package com.devbank.accounting.api.service;

import com.devbank.accounting.api.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Long accountId, AccountDTO accountDTO);
    AccountDTO getAccountById(Long accountId);
    List<AccountDTO> getAccountsByCustomerId(Long customerId);
    List<AccountDTO> getAllAccounts();
    void deleteAccount(Long accountId);
    void updateAccountBalance(Long accountId, Double newBalance);
}
