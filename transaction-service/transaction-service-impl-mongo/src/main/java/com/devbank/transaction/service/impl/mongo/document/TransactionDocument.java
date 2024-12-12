package com.devbank.transaction.service.impl.mongo.document;

import com.devbank.transaction.service.api.enums.TransactionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
public class TransactionDocument {
    @Id
    private String id;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private TransactionType transactionType;
    private LocalDateTime timestamp;
}
