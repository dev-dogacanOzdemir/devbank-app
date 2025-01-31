package com.devbank.card.management.api.DTO;

import com.devbank.card.management.api.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardTransactionDTO {
    private String transactionId;

    @NotBlank(message = "Card ID is required")
    private String cardId;

    @NotBlank(message = "Account ID is required for Debit Cards")
    private String accountId;

    @NotNull(message = "Transaction amount is required")
    private Double amount;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    private Date transactionDate;
}
