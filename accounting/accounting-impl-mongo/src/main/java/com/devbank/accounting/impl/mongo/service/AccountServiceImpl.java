package com.devbank.accounting.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import com.devbank.accounting.impl.mongo.mapper.AccountMapper;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import com.devbank.error.management.exception.AccountNotFoundException;
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
        AccountDocument document = accountMapper.toDocument(accountDTO);
        document.setCreatedAt(new java.util.Date());
        AccountDocument savedDocument = accountRepository.save(document);
        return accountMapper.toDTO(savedDocument);
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));
    }

    @Override
    public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
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
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        AccountDocument existingDocument = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + accountId));

        // Alanları güncelle
        existingDocument.setAccountType(accountDTO.getAccountType());
        existingDocument.setBalance(accountDTO.getBalance());
        existingDocument.setInterestRate(accountDTO.getInterestRate());
        existingDocument.setMaturityDate(accountDTO.getMaturityDate());
        // Created at değiştirilemez
        if (accountDTO.getCreatedAt() != null) {
            throw new IllegalArgumentException("CreatedAt cannot be modified");
        }
        // Güncellenmiş hesabı kaydet
        AccountDocument updatedDocument = accountRepository.save(existingDocument);
        return accountMapper.toDTO(updatedDocument);
    }

    @Override
    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }

    public void updateAccountBalance(Long accountId, Double newBalance) {
        AccountDocument account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(newBalance);
        accountRepository.save(account); // Yeni bakiye güncellemesi
    }

}
