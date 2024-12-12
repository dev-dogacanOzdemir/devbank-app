package com.devbank.transaction.service.endpoint.controller;


import com.devbank.transaction.service.api.DTO.TransactionDTO;
import com.devbank.transaction.service.api.enums.TransactionType;
import com.devbank.transaction.service.impl.mongo.document.TransactionDocument;
import com.devbank.transaction.service.impl.mongo.service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    // Transfer işlemi için endpoint
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public TransactionDocument transfer(@RequestBody TransactionDTO transactionDTO) {
        if (transactionDTO.getTransactionType() != TransactionType.TRANSFER) {
            throw new IllegalArgumentException("Invalid transaction type for transfer.");
        }
        return transactionService.transfer(transactionDTO);
    }


    // Para yatırma işlemi için endpoint
    @PostMapping("/deposit/{accountNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public TransactionDocument deposit(@PathVariable String accountNumber, @RequestParam double amount) {
        return transactionService.deposit(accountNumber, amount);
    }


    // Para çekme işlemi için endpoint
    @PostMapping("/withdraw/{accountNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public TransactionDocument withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        return transactionService.withdraw(accountNumber, amount);
    }
}
