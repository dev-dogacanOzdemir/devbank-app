package com.devbank.transfer.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.error.management.exception.CustomException;
import com.devbank.error.management.exception.TransferFailedException;
import com.devbank.error.management.exception.TransferNotFoundException;
import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.api.service.TransferService;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.mapper.TransferMapper;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final AccountService accountService;

    @Override
    public TransferDTO createTransfer(TransferDTO transferDTO) {
        try {
            // Gönderici ve alıcı hesapları al
            AccountDTO senderAccount = accountService.getAccountById(transferDTO.getSenderAccountId());
            AccountDTO receiverAccount = accountService.getAccountById(transferDTO.getReceiverAccountId());

            // Hesap türü kontrolü (sadece CURRENT hesaplar)
            if (senderAccount.getAccountType() != AccountType.CURRENT || receiverAccount.getAccountType() != AccountType.CURRENT) {
                throw new CustomException("Transfers are only allowed between CURRENT account types.");
            }

            // Bakiye kontrolü
            if (senderAccount.getBalance() < transferDTO.getAmount()) {
                throw new CustomException("Insufficient balance in sender's account.");
            }

            // Hesap bakiyelerini güncelle
            accountService.updateAccountBalance(senderAccount.getAccountId(),
                    senderAccount.getBalance() - transferDTO.getAmount());
            accountService.updateAccountBalance(receiverAccount.getAccountId(),
                    receiverAccount.getBalance() + transferDTO.getAmount());

            // Transfer kaydını oluştur ve kaydet
            transferDTO.setId(null);
            transferDTO.setStatus(TransferStatus.COMPLETED);
            transferDTO.setTransferTime(LocalDateTime.now());
            TransferDocument document = transferMapper.toDocument(transferDTO);
            TransferDocument savedDocument = transferRepository.save(document);
            return transferMapper.toDTO(savedDocument);

        } catch (Exception e) {
            // Transfer başarısız olduysa kaydet ve hata fırlat
            transferDTO.setStatus(TransferStatus.FAILED);
            transferDTO.setTransferTime(LocalDateTime.now());
            TransferDocument failedDocument = transferMapper.toDocument(transferDTO);
            transferRepository.save(failedDocument);
            throw new TransferFailedException("Transfer failed: " + e.getMessage());
        }
    }

    @Override
    public TransferDTO createScheduledTransfer(TransferDTO transferDTO) {
        transferDTO.setId(null);
        transferDTO.setStatus(TransferStatus.PENDING);
        transferDTO.setTransferTime(transferDTO.getTransferTime());
        TransferDocument document = transferMapper.toDocument(transferDTO);
        TransferDocument savedDocument = transferRepository.save(document);
        return transferMapper.toDTO(savedDocument);
    }

    public List<TransferDTO> getAllTransfers() {
        List<TransferDocument> transferDocuments = transferRepository.findAll();
        return transferDocuments.stream()
                .map(transferMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<TransferDTO> getTransfersByAccountId(String accountId) {
        List<TransferDocument> transfers = transferRepository.findBySenderAccountIdOrReceiverAccountId(accountId, accountId);
        return transfers.stream().map(transferMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TransferDTO updateTransferStatus(String transferId, TransferStatus status) {
        TransferDocument document = transferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found with id: " + transferId));
        document.setStatus(status);
        TransferDocument updatedDocument = transferRepository.save(document);
        return transferMapper.toDTO(updatedDocument);
    }
}
