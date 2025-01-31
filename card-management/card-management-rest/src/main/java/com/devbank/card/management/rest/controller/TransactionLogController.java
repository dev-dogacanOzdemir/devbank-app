package com.devbank.card.management.rest.controller;

import com.devbank.card.management.impl.mongo.document.CardTransactionLogDocument;
import com.devbank.card.management.impl.mongo.repository.CardTransactionLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transaction-logs")
public class TransactionLogController {

    private final CardTransactionLogRepository transactionLogRepository;

    public TransactionLogController(CardTransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<CardTransactionLogDocument>> getLogsByCardId(@PathVariable String cardId) {
        return ResponseEntity.ok(transactionLogRepository.findByCardId(cardId));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CardTransactionLogDocument>> getLogsByAccountId(@PathVariable String accountId) {
        return ResponseEntity.ok(transactionLogRepository.findByAccountId(accountId));
    }
}
