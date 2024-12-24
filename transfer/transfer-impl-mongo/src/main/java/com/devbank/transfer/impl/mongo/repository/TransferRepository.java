package com.devbank.transfer.impl.mongo.repository;

import com.devbank.transfer.impl.mongo.document.TransferDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransferRepository extends MongoRepository<TransferDocument, Long> {
    List<TransferDocument> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
    List<TransferDocument> findByCustomerId(Long customerId);
}
