package com.devbank.card.management.impl.mongo.mapper;

import com.devbank.card.management.api.DTO.CardDTO;
import com.devbank.card.management.impl.mongo.document.CardDocument;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardDTO toDTO(CardDocument document) {
        return new CardDTO(
                document.getId(),
                document.getUserId(),
                document.getAccountId(),
                document.getCardNumber(),
                document.getCardType(),
                document.getStatus(),
                document.getCreditLimit(),
                document.getBalance(),
                document.getExpirationDate()
        );
    }

    public CardDocument toDocument(CardDTO dto) {
        return new CardDocument(
                dto.getId(),
                dto.getUserId(),
                dto.getAccountId(),
                dto.getCardNumber(),
                dto.getCardType(),
                dto.getStatus(),
                dto.getCreditLimit(),
                dto.getBalance(),
                dto.getExpirationDate()
        );
    }
}
