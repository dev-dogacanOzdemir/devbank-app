package com.devbank.transfer.impl.mongo.scheduler;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.mapper.TransferMapper;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferScheduler {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final AccountService accountService;

    @Scheduled(fixedRate = 60000) // Her 1 dakikada bir çalışır
    public void processScheduledTransfers() {
        // PENDING durumunda ve transfer zamanı geçmiş olanları al
        List<TransferDocument> pendingTransfers = transferRepository.findByStatusAndTransferTimeBefore(
                TransferStatus.PENDING, LocalDateTime.now());

        for (TransferDocument transfer : pendingTransfers) {
            try {
                // Gönderici ve alıcı hesapları kontrol et
                AccountDTO senderAccount = accountService.getAccountById(transfer.getSenderAccountId());
                AccountDTO receiverAccount = accountService.getAccountById(transfer.getReceiverAccountId());

                // Gönderici bakiyesi kontrolü
                if (senderAccount.getBalance() < transfer.getAmount()) {
                    transfer.setStatus(TransferStatus.FAILED);
                } else {
                    // Bakiyeleri güncelle
                    accountService.updateAccountBalance(senderAccount.getAccountId(),
                            senderAccount.getBalance() - transfer.getAmount());
                    accountService.updateAccountBalance(receiverAccount.getAccountId(),
                            receiverAccount.getBalance() + transfer.getAmount());

                    transfer.setStatus(TransferStatus.COMPLETED);
                }
            } catch (Exception e) {
                // Herhangi bir hata durumunda FAILED olarak işaretle
                transfer.setStatus(TransferStatus.FAILED);
            }

            // Transfer durumunu güncelle
            transferRepository.save(transfer);
        }
    }
}
