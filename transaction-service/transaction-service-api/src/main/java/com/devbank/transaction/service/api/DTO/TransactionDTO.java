package com.devbank.transaction.service.api.DTO;

import com.devbank.transaction.service.api.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private String id;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private TransactionType transactionType;
    private LocalDateTime timestamp;
}
