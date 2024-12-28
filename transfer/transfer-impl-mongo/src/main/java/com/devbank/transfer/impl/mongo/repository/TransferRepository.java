package com.devbank.transfer.impl.mongo.repository;

import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransferRepository extends MongoRepository<TransferDocument, String> {
    // Gönderici veya alıcı olarak ilgili hesap ID'ye sahip transferleri bul
    List<TransferDocument> findBySenderAccountIdOrReceiverAccountId(String accountId1, String accountId2);

    // PENDING transferleri kontrol etmek için
    List<TransferDocument> findByStatusAndTransferTimeBefore(TransferStatus status, LocalDateTime transferTime);
}
