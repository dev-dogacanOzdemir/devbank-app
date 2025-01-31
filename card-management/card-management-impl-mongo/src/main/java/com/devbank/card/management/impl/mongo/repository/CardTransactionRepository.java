package com.devbank.card.management.impl.mongo.repository;

import com.devbank.card.management.impl.mongo.document.CardTransactionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardTransactionRepository extends MongoRepository<CardTransactionDocument,String> {

    List<CardTransactionDocument> findByCardId(String cardId);
}
