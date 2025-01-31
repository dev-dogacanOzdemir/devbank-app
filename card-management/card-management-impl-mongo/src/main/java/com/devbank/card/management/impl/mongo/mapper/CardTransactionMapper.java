package com.devbank.card.management.impl.mongo.mapper;

import com.devbank.card.management.api.DTO.CardTransactionDTO;
import com.devbank.card.management.impl.mongo.document.CardTransactionDocument;
import org.springframework.stereotype.Component;

@Component
public class CardTransactionMapper {

    public CardTransactionDTO toDTO(CardTransactionDocument document) {
        return new CardTransactionDTO(
                document.getTransactionId(),
                document.getCardId(),
                document.getAccountId(),
                document.getAmount(),
                document.getTransactionType(),
                document.getTransactionDate()
        );
    }

    public CardTransactionDocument toDocument(CardTransactionDTO dto) {
        return new CardTransactionDocument(
                dto.getTransactionId(),
                dto.getCardId(),
                dto.getAccountId(),
                dto.getAmount(),
                dto.getTransactionType(),
                dto.getTransactionDate()
        );
    }

}
