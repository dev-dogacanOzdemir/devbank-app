package com.devbank.transfer.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.error.management.exception.TransferFailedException;
import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.mapper.TransferMapper;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferServiceImplTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private TransferMapper transferMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Disabled
    @Test
    void testCreateTransfer_Success() {
        // Arrange
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSenderAccountId("sender123");
        transferDTO.setReceiverAccountId("receiver123");
        transferDTO.setAmount(500.0);
        transferDTO.setTransferTime(LocalDateTime.now());AccountDTO senderAccount = new AccountDTO(
                "account123",  // accountId
                "sender123",   // customerId
                AccountType.CURRENT, // accountType
                1000.0, // balance
                "TR0011223344556677", // uniqueAccountNumber
                new Date(), // createdAt
                null, // interestRate (Vadeli hesap değilse null olabilir)
                null  // maturityDate (Vadeli hesap değilse null olabilir)
        );

        AccountDTO receiverAccount = new AccountDTO(
                "account456",
                "receiver123",
                AccountType.CURRENT,
                200.0,
                "TR9988776655443322",
                new Date(),
                null,
                null
        );

        TransferDocument document = new TransferDocument();
        TransferDocument savedDocument = new TransferDocument();

        TransferDTO savedDTO = new TransferDTO();
        savedDTO.setStatus(TransferStatus.COMPLETED);
        savedDTO.setTransferTime(LocalDateTime.now());

        when(accountService.getAccountById("sender123")).thenReturn(senderAccount);
        when(accountService.getAccountById("receiver123")).thenReturn(receiverAccount);
        when(transferMapper.toDocument(transferDTO)).thenReturn(document);
        when(transferRepository.save(document)).thenReturn(savedDocument);
        when(transferMapper.toDTO(savedDocument)).thenReturn(savedDTO);

        // Act
        TransferDTO result = transferService.createTransfer(transferDTO);

        // Assert
        assertEquals(TransferStatus.COMPLETED, result.getStatus());
        verify(accountService).updateAccountBalance("sender123", 500.0);
        verify(accountService).updateAccountBalance("receiver123", 700.0);
        verify(transferRepository).save(document);
    }

    @Test
    void testCreateTransfer_FailedDueToInsufficientBalance() {
        // Mock hesaplar
        when(accountService.getAccountById("sender-id")).thenReturn(
                new AccountDTO("sender-id", "customer-id", AccountType.CURRENT, 50.0, "IBAN123", new Date(), null, null)
        );
        when(accountService.getAccountById("receiver-id")).thenReturn(
                new AccountDTO("receiver-id", "customer-id", AccountType.CURRENT, 500.0, "IBAN456", new Date(), null, null)
        );

        // Transfer DTO oluştur
        TransferDTO transferDTO = new TransferDTO(null, "sender-id", "receiver-id", 100.0, "Test transfer",
                TransferStatus.PENDING, LocalDateTime.now());

        // Hata kontrolü
        assertThrows(TransferFailedException.class, () -> {
            transferService.createTransfer(transferDTO);
        });
    }

    @Test
    void testCreateTransfer_FailedDueToInvalidAccountType() {
        // Mock hesaplar
        when(accountService.getAccountById("sender-id")).thenReturn(
                new AccountDTO("sender-id", "customer-id", AccountType.SAVINGS, 1000.0, "IBAN123", new Date(), null, null)
        );
        when(accountService.getAccountById("receiver-id")).thenReturn(
                new AccountDTO("receiver-id", "customer-id", AccountType.CURRENT, 500.0, "IBAN456", new Date(), null, null)
        );

        TransferDTO transferDTO = new TransferDTO(
                null,
                "sender-id",
                "receiver-id",
                100.0,
                "Invalid account type test",
                TransferStatus.PENDING,
                LocalDateTime.now()
        );

        assertThrows(TransferFailedException.class, () -> {
            transferService.createTransfer(transferDTO);
        });
    }

    @Test
    void testGetTransfersByAccountId() {
        // Arrange
        String accountId = "sender123";

        TransferDocument document1 = new TransferDocument();
        TransferDocument document2 = new TransferDocument();
        when(transferRepository.findBySenderAccountIdOrReceiverAccountId(accountId, accountId))
                .thenReturn(List.of(document1, document2));

        TransferDTO dto1 = new TransferDTO();
        TransferDTO dto2 = new TransferDTO();
        when(transferMapper.toDTO(document1)).thenReturn(dto1);
        when(transferMapper.toDTO(document2)).thenReturn(dto2);

        // Act
        List<TransferDTO> transfers = transferService.getTransfersByAccountId(accountId);

        // Assert
        assertEquals(2, transfers.size());
        verify(transferRepository).findBySenderAccountIdOrReceiverAccountId(accountId, accountId);
    }
}
