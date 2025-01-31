package com.devbank.card.management.impl.mongo.document;

import com.devbank.card.management.api.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "card_transactions")
public class CardTransactionDocument {

    @Id
    private String transactionId;
    private String cardId;
    private String accountId;
    private Double amount;
    private TransactionType transactionType;
    private Date transactionDate;
}
