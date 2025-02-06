package com.devbank.loan.management.impl.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "loan_payments")
public class LoanPaymentDocument {

    @Id
    private String id;
    private String loanId;
    private double paymentAmount;
    private LocalDateTime paymentDate;
}
