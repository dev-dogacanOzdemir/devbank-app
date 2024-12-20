package com.devbank.accounting.impl.mongo.repository;

import com.devbank.accounting.impl.mongo.document.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<AccountDocument, Long> {

    List<AccountDocument> findByCustomerId(Long customerId);
}
