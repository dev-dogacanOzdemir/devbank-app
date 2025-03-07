package com.devbank.accounting.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import com.devbank.accounting.impl.mongo.mapper.AccountMapper;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import com.devbank.error.management.exception.AccountNotFoundException;
import com.devbank.error.management.exception.CustomException;
import com.devbank.error.management.exception.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (accountDTO.getUniqueAccountNumber() == null || accountDTO.getUniqueAccountNumber().isEmpty()) {
            throw new CustomException("Unique Account Number cannot be null or empty.");
        }
        if (accountDTO.getUniqueAccountNumber() != null &&
                !accountDTO.getUniqueAccountNumber().matches("^(TR|USD)[0-9]{16}$")) {
            throw new IllegalArgumentException("Invalid IBAN format. Expected format: TR1234567891234567 or USD1234567891234567.");
        }
        AccountDocument document = accountMapper.toDocument(accountDTO);
        document.setCreatedAt(new java.util.Date());
        AccountDocument savedDocument = accountRepository.save(document);
        return accountMapper.toDTO(savedDocument);
    }

    @Override
    public AccountDTO getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
    }

    @Override
    public List<AccountDTO> getAccountsByCustomerId(String customerId) {
        List<AccountDocument> documents = accountRepository.findByCustomerId(customerId);
        return documents.stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<AccountDocument> accountDocuments = accountRepository.findAll();
        return accountDocuments.stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccount(String accountId, AccountDTO accountDTO) {
        AccountDocument existingDocument = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        existingDocument.setAccountType(accountDTO.getAccountType());
        existingDocument.setBalance(accountDTO.getBalance());
        existingDocument.setInterestRate(accountDTO.getInterestRate());
        existingDocument.setMaturityDate(accountDTO.getMaturityDate());
        if (accountDTO.getCreatedAt() != null) {
            throw new IllegalArgumentException("CreatedAt cannot be modified");
        }
        AccountDocument updatedDocument = accountRepository.save(existingDocument);
        return accountMapper.toDTO(updatedDocument);
    }

    @Override
    public void deleteAccount(String accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    public void updateAccountBalance(String accountId, Double newBalance) {
        AccountDocument account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void withdrawFromAccount(String accountId, Double amount) {
        AccountDocument account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient funds in account: " + accountId);
        }

        // Yeni bakiye hesaplanarak updateAccountBalance çağrılıyor
        updateAccountBalance(accountId, account.getBalance() - amount);
    }

}
