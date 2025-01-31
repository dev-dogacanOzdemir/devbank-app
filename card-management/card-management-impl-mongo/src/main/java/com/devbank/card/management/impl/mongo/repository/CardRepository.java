package com.devbank.card.management.impl.mongo.repository;

import com.devbank.card.management.impl.mongo.document.CardDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<CardDocument, String> {

    List<CardDocument> findByUserId(String userId);
}
