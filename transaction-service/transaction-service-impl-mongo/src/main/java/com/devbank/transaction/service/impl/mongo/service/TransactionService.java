package com.devbank.transaction.service.impl.mongo.service;


import com.devbank.account.api.enums.AccountType;
import com.devbank.account.impl.mongo.document.AccountDocument;
import com.devbank.account.impl.mongo.service.AccountService;
import com.devbank.error.management.exception.CustomException;
import com.devbank.transaction.service.api.DTO.TransactionDTO;
import com.devbank.transaction.service.api.enums.TransactionType;
import com.devbank.transaction.service.impl.mongo.document.TransactionDocument;
import com.devbank.transaction.service.impl.mongo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService){
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public TransactionDocument transfer(TransactionDTO transactionDTO, String role) {
        // BUSINESS_USER sadece ticari hesaplar (örneğin, CURRENT) arasında transfer yapabilir
        if (role.equals("ROLE_BUSINESS_USER")) {
            AccountDocument fromAccount = accountService.getAccountByNumber(transactionDTO.getFromAccount());
            AccountDocument toAccount = accountService.getAccountByNumber(transactionDTO.getToAccount());

            if (fromAccount.getAccountType() != AccountType.CURRENT || toAccount.getAccountType() != AccountType.CURRENT) {
                throw new CustomException("BUSINESS_USER can only transfer between CURRENT accounts", HttpStatus.FORBIDDEN);
            }
        }

        // Normal transfer mantığı
        TransactionDocument transaction = new TransactionDocument();
        transaction.setFromAccount(transactionDTO.getFromAccount());
        transaction.setToAccount(transactionDTO.getToAccount());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public TransactionDocument transfer(TransactionDTO transactionDTO) {

        // Normal transfer mantığı
        TransactionDocument transaction = new TransactionDocument();
        transaction.setFromAccount(transactionDTO.getFromAccount());
        transaction.setToAccount(transactionDTO.getToAccount());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public TransactionDocument deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new CustomException("Deposit amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        TransactionDocument transaction = new TransactionDocument();
        transaction.setFromAccount(null); // Para yatırmada kaynak hesap yok
        transaction.setToAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT); // Deposit tipi
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public TransactionDocument withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new CustomException("Withdrawal amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        TransactionDocument transaction = new TransactionDocument();
        transaction.setFromAccount(accountNumber);
        transaction.setToAccount(null); // Para çekmede hedef hesap yok
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAWAL); // Withdrawal tipi
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

}
