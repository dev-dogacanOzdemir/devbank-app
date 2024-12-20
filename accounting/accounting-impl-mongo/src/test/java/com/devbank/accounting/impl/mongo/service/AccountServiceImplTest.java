package com.devbank.accounting.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import com.devbank.accounting.impl.mongo.mapper.AccountMapper;
import com.devbank.accounting.impl.mongo.repository.AccountRepository;
import com.devbank.error.management.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        AccountDTO accountDTO = new AccountDTO(null, 1001L, AccountType.CURRENT, 5000.0, null, null, null);
        AccountDocument accountDocument = new AccountDocument(null, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);
        AccountDocument savedDocument = new AccountDocument(1L, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);
        AccountDTO expectedDTO = new AccountDTO(1L, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);

        when(accountMapper.toDocument(accountDTO)).thenReturn(accountDocument);
        when(accountRepository.save(accountDocument)).thenReturn(savedDocument);
        when(accountMapper.toDTO(savedDocument)).thenReturn(expectedDTO);

        AccountDTO result = accountService.createAccount(accountDTO);

        assertNotNull(result);
        assertEquals(1L, result.getAccountId());
        verify(accountRepository, times(1)).save(accountDocument);
    }

    @Test
    void testGetAccountById_Success() {
        AccountDocument accountDocument = new AccountDocument(1L, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);
        AccountDTO accountDTO = new AccountDTO(1L, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountDocument));
        when(accountMapper.toDTO(accountDocument)).thenReturn(accountDTO);

        AccountDTO result = accountService.getAccountById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAccountId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAccountById_NotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(1L));

        assertEquals("Account not found with ID: 1", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAccount_Success() {
        AccountDTO accountDTO = new AccountDTO(1L, 1001L, AccountType.CURRENT, 8000.0, null, null, null);
        AccountDocument existingDocument = new AccountDocument(1L, 1001L, AccountType.CURRENT, 5000.0, new Date(), null, null);
        AccountDocument updatedDocument = new AccountDocument(1L, 1001L, AccountType.CURRENT, 8000.0, new Date(), null, null);
        AccountDTO expectedDTO = new AccountDTO(1L, 1001L, AccountType.CURRENT, 8000.0, new Date(), null, null);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingDocument));
        when(accountRepository.save(existingDocument)).thenReturn(updatedDocument);
        when(accountMapper.toDTO(updatedDocument)).thenReturn(expectedDTO);

        AccountDTO result = accountService.updateAccount(1L, accountDTO);

        assertNotNull(result);
        assertEquals(8000.0, result.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(existingDocument);
    }

    @Test
    void testDeleteAccount_Success() {
        when(accountRepository.existsById(1L)).thenReturn(true);

        accountService.deleteAccount(1L);

        verify(accountRepository, times(1)).existsById(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAccount_NotFound() {
        when(accountRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(1L));

        assertEquals("Account not found with ID: 1", exception.getMessage());
        verify(accountRepository, times(1)).existsById(1L);
        verify(accountRepository, never()).deleteById(1L);
    }
}
