package com.devbank.card.management.impl.mongo.document;

import com.devbank.card.management.api.enums.CardStatus;
import com.devbank.card.management.api.enums.CardType;
import com.devbank.error.management.exception.InvalidCardException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cards")
public class CardDocument {

    @Id
    private String id;
    private String userId;
    private String accountId;
    private String cardNumber;
    private CardType cardType;
    private CardStatus status;
    private Double creditLimit;
    private Double balance;
    private Date expirationDate;

    public void validate() {
        if (this.cardType == CardType.DEBIT_CARD && (this.accountId == null || this.accountId.isEmpty())) {
            throw new InvalidCardException("Debit cards must have an associated account ID.");
        }
    }
}
