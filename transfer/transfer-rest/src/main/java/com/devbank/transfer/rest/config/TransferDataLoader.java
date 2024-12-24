package com.devbank.transfer.rest.config;

import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransferDataLoader implements CommandLineRunner {

    private final TransferRepository transferRepository;

    public TransferDataLoader(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public void run(String... args) {
        if (transferRepository.count() == 0) {
            TransferDocument transfer = new TransferDocument(1L, 1L, 2L, 500.0, new Date(), "USD", TransferStatus.COMPLETED);
            transferRepository.save(transfer);
            System.out.println("Transfer initial data loaded.");
        }
    }
}
