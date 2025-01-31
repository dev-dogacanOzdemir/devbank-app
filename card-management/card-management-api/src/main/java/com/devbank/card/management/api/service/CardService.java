package com.devbank.card.management.api.service;

import com.devbank.card.management.api.DTO.CardDTO;
import com.devbank.card.management.api.DTO.CardTransactionDTO;
import com.devbank.card.management.api.enums.CardStatus;

import java.util.List;

public interface CardService {

    CardDTO createCard(CardDTO cardDTO);
    CardDTO getCardById(String cardId);
    List<CardDTO> getCardsByUserId(String userId);
    CardDTO updateCardStatus(String cardId, CardStatus status);
    CardDTO updateCardLimit(String cardId, Double newLimit);
    void processTransaction(CardTransactionDTO transactionDTO);

}
