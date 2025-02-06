package com.devbank.loan.management.impl.mongo.repository;

import com.devbank.loan.management.api.enums.LoanStatus;
import com.devbank.loan.management.impl.mongo.document.LoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends MongoRepository<LoanDocument,String> {

    List<LoanDocument> findByCustomerId(String customerId);
    List<LoanDocument> findByStatus(LoanStatus status);
}
