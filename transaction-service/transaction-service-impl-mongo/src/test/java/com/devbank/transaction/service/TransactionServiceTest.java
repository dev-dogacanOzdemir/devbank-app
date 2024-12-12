package com.devbank.transaction.service;

import com.devbank.account.impl.mongo.service.AccountService;
import com.devbank.error.management.exception.CustomException;
import com.devbank.transaction.service.api.DTO.TransactionDTO;
import com.devbank.transaction.service.impl.mongo.document.TransactionDocument;
import com.devbank.transaction.service.impl.mongo.repository.TransactionRepository;
import com.devbank.transaction.service.impl.mongo.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class TransactionServiceTest {

    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private final AccountService accountService = Mockito.mock(AccountService.class);
    private final TransactionService transactionService = new TransactionService(transactionRepository,accountService);

    @Test
    public void testTransfer_Success() {
        // Mock AccountService'ten yeterli bakiye döndür
        Mockito.when(accountService.getBalance("123456")).thenReturn(200.0);

        // Mock TransactionRepository'de kaydedilecek TransactionDocument'i döndür
        Mockito.when(transactionRepository.save(Mockito.any(TransactionDocument.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Kaydedilen nesneyi döndür

        // TransactionDTO oluştur
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromAccount("123456");
        transactionDTO.setToAccount("654321");
        transactionDTO.setAmount(100.0);

        // Transfer çağrısını yap
        TransactionDocument result = transactionService.transfer(transactionDTO);

        // Sonuçları doğrula
        assertNotNull(result);
        assertEquals("123456", result.getFromAccount());
        assertEquals("654321", result.getToAccount());
        assertEquals(100.0, result.getAmount());
        assertNotNull(result.getTimestamp());
    }

    @Test
    public void testTransfer_InsufficientBalance() {
        // Mock bir AccountService oluştur ve yetersiz bakiye döndür
        Mockito.when(accountService.getBalance("123456")).thenReturn(50.0);

        // TransactionDTO oluştur
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromAccount("123456");
        transactionDTO.setToAccount("654321");
        transactionDTO.setAmount(100.0); // Gönderim için yeterli değil

        // İstisnayı doğrula
        CustomException exception = assertThrows(CustomException.class, () -> {
            transactionService.transfer(transactionDTO);
        });

        // Hata mesajını doğrula
        assertEquals("Insufficient balance in account 123456", exception.getMessage());
    }

    @Test
    public void testTransfer_SameAccount() {
        // Mock AccountService
        Mockito.when(accountService.getBalance("123456")).thenReturn(200.0);

        // TransactionDTO oluştur
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromAccount("123456");
        transactionDTO.setToAccount("123456"); // Aynı hesap
        transactionDTO.setAmount(100.0);

        // İstisna kontrolü
        CustomException exception = assertThrows(CustomException.class, () -> {
            transactionService.transfer(transactionDTO);
        });

        // Hata mesajını doğrula
        assertEquals("Cannot transfer to the same account", exception.getMessage());
    }
}
