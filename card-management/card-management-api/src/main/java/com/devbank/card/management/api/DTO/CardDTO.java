package com.devbank.card.management.api.DTO;

import com.devbank.card.management.api.enums.CardStatus;
import com.devbank.card.management.api.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Account ID is required for Debit Cards")
    private String accountId;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Card type is required")
    private CardType cardType;

    @NotNull(message = "Card status is required")
    private CardStatus status;

    @NotNull(message = "Card limit is required")
    private Double creditLimit;

    @NotNull(message = "Current balance is required")
    private Double balance;

    private Date expirationDate;
}
