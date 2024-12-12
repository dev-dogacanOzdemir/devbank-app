package com.devbank.transaction.service.impl.mongo.repository;


import com.devbank.transaction.service.impl.mongo.document.TransactionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<com.devbank.transaction.service.impl.mongo.document.TransactionDocument, String> {
}
