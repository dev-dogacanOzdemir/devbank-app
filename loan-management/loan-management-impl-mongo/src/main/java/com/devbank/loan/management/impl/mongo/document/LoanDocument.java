package com.devbank.loan.management.impl.mongo.document;

import com.devbank.loan.management.api.enums.LoanStatus;
import com.devbank.loan.management.api.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loans")
public class LoanDocument {

    @Id
    private String id;
    private String customerId;
    private double amount;
    private int termInMonths;
    private double interestRate;
    private LoanType loanType;
    private LoanStatus status;
    private String approvedBy;
    private LocalDateTime createdAt;
    private List<LoanPaymentDocument> payments;
}
