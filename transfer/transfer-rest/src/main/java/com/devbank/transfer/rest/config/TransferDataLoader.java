package com.devbank.transfer.rest.config;

import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransferDataLoader implements CommandLineRunner {

    private final TransferRepository transferRepository;

    public TransferDataLoader(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public void run(String... args) {
        if (transferRepository.count() == 0) {
            TransferDocument transfer = new TransferDocument(
                    null, // ID otomatik oluşturulur
                    "1L", // Gönderici hesap ID
                    "2L", // Alıcı hesap ID
                    500.0, // Transfer miktarı
                    "Initial transfer", // Açıklama
                    TransferStatus.COMPLETED, // Durum
                    LocalDateTime.now() // Transfer zamanı
            );
            transferRepository.save(transfer);
            System.out.println("Transfer initial data loaded.");
        }
    }
}
