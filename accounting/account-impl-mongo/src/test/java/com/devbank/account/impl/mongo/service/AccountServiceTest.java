package com.devbank.account.impl.mongo.service;

import com.devbank.account.api.DTO.AccountDTO;
import com.devbank.account.api.enums.AccountType;
import com.devbank.account.impl.mongo.document.AccountDocument;
import com.devbank.account.impl.mongo.repository.AccountRepository;
import com.devbank.error.management.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class AccountServiceTest {

    private final AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private final AccountService accountService = new AccountService(accountRepository);

    // Test: getAccountByNumber - Başarılı
    @Test
    public void testGetAccountByNumber_Success() {
        AccountDocument mockAccount = new AccountDocument();
        mockAccount.setAccountNumber("123456");
        mockAccount.setOwnerUsername("john_doe");
        mockAccount.setBalance(1000.0);
        mockAccount.setAccountType(AccountType.SAVINGS);

        Mockito.when(accountRepository.findByAccountNumber("123456"))
                .thenReturn(Optional.of(mockAccount));

        AccountDocument result = accountService.getAccountByNumber("123456", "john_doe");

        assertEquals("123456", result.getAccountNumber());
        assertEquals("john_doe", result.getOwnerUsername());
        assertEquals(1000.0, result.getBalance());
    }

    // Test: getAccountByNumber - Hesap bulunamadı
    @Test
    public void testGetAccountByNumber_NotFound() {
        Mockito.when(accountRepository.findByAccountNumber("123456"))
                .thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            accountService.getAccountByNumber("123456", "john_doe");
        });

        assertEquals("Account not found", exception.getMessage());
    }

    // Test: getAccountByNumber - Erişim reddedildi
    @Test
    public void testGetAccountByNumber_AccessDenied() {
        AccountDocument mockAccount = new AccountDocument();
        mockAccount.setAccountNumber("123456");
        mockAccount.setOwnerUsername("different_user");
        mockAccount.setSharedUsernames(List.of("shared_user"));

        Mockito.when(accountRepository.findByAccountNumber("123456"))
                .thenReturn(Optional.of(mockAccount));

        CustomException exception = assertThrows(CustomException.class, () -> {
            accountService.getAccountByNumber("123456", "unauthorized_user");
        });

        assertEquals("Access denied", exception.getMessage());
    }

    // Test: createAccount - Başarılı
    @Test
    public void testCreateAccount_Success() {
        AccountDTO newAccountDTO = new AccountDTO();
        newAccountDTO.setAccountNumber("654321");
        newAccountDTO.setCustomerName("Jane Doe");
        newAccountDTO.setAccountType(AccountType.CURRENT);

        AccountDocument savedAccount = new AccountDocument();
        savedAccount.setAccountNumber("654321");
        savedAccount.setOwnerUsername("Jane Doe");
        savedAccount.setAccountType(AccountType.CURRENT);
        savedAccount.setBalance(0.0);

        Mockito.when(accountRepository.existsByAccountNumber("654321")).thenReturn(false);
        Mockito.when(accountRepository.save(any(AccountDocument.class))).thenReturn(savedAccount);

        AccountDocument result = accountService.createAccount(newAccountDTO);

        assertEquals("654321", result.getAccountNumber());
        assertEquals("Jane Doe", result.getOwnerUsername());
        assertEquals(AccountType.CURRENT, result.getAccountType());
        assertEquals(0.0, result.getBalance());
    }

    // Test: createAccount - Hesap zaten mevcut
    @Test
    public void testCreateAccount_AccountExists() {
        Mockito.when(accountRepository.existsByAccountNumber("654321")).thenReturn(true);

        AccountDTO newAccountDTO = new AccountDTO();
        newAccountDTO.setAccountNumber("654321");

        CustomException exception = assertThrows(CustomException.class, () -> {
            accountService.createAccount(newAccountDTO);
        });

        assertEquals("Account with number 654321 already exists.", exception.getMessage());
    }

    // Test: getAccountsByOwner - Başarılı
    @Test
    public void testGetAccountsByOwner_Success() {
        AccountDocument account1 = new AccountDocument();
        account1.setAccountNumber("111111");
        account1.setOwnerUsername("john_doe");

        AccountDocument account2 = new AccountDocument();
        account2.setAccountNumber("222222");
        account2.setOwnerUsername("john_doe");

        Mockito.when(accountRepository.findByOwnerUsername("john_doe"))
                .thenReturn(List.of(account1, account2));

        List<AccountDocument> result = accountService.getAccountsByOwner("john_doe");

        assertEquals(2, result.size());
    }

    // Test: getSharedAccounts - Başarılı
    @Test
    public void testGetSharedAccounts_Success() {
        AccountDocument sharedAccount = new AccountDocument();
        sharedAccount.setAccountNumber("123456");
        sharedAccount.setSharedUsernames(List.of("shared_user"));

        Mockito.when(accountRepository.findBySharedUsernamesContaining("shared_user"))
                .thenReturn(List.of(sharedAccount));

        List<AccountDocument> result = accountService.getSharedAccounts("shared_user");

        assertEquals(1, result.size());
    }
}
