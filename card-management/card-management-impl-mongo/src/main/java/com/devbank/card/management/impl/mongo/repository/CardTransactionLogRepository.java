package com.devbank.card.management.impl.mongo.repository;

import com.devbank.card.management.impl.mongo.document.CardTransactionLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardTransactionLogRepository extends MongoRepository<CardTransactionLogDocument,String> {

    List<CardTransactionLogDocument> findByCardId(String cardId);  // 🔹 Belirli bir kartın loglarını getirir
    List<CardTransactionLogDocument> findByAccountId(String accountId);  // 🔹 Belirli bir hesabın loglarını getirir
}
