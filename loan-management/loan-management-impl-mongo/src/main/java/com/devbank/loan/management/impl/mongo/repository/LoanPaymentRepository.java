package com.devbank.loan.management.impl.mongo.repository;

import com.devbank.loan.management.impl.mongo.document.LoanPaymentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentRepository extends MongoRepository<LoanPaymentDocument,String> {

    List<LoanPaymentDocument> findByLoanId(String loanId);
}
