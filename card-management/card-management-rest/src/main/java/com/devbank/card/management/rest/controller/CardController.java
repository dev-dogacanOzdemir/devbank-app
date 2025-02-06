package com.devbank.card.management.rest.controller;

import com.devbank.card.management.api.DTO.CardDTO;
import com.devbank.card.management.api.DTO.CardTransactionDTO;
import com.devbank.card.management.api.enums.CardStatus;
import com.devbank.card.management.api.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO : Bazı işlemler için öncelikle CUSTOMER istek gönderip ADMIN tarafından kabul edildiğinde gerekli düzenlemeler yapılmalıdır.
@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.createCard(cardDTO));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable String cardId) {
        return ResponseEntity.ok(cardService.getCardById(cardId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardDTO>> getCardsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(cardService.getCardsByUserId(userId));
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<CardDTO> updateCardStatus(@PathVariable String cardId, @RequestParam CardStatus status) {
        return ResponseEntity.ok(cardService.updateCardStatus(cardId, status));
    }

    @PutMapping("/{cardId}/limit")
    public ResponseEntity<CardDTO> updateCardLimit(@PathVariable String cardId, @RequestParam Double newLimit) {
        return ResponseEntity.ok(cardService.updateCardLimit(cardId, newLimit));
    }

    @PostMapping("/{cardId}/transactions")
    public ResponseEntity<Void> processTransaction(@PathVariable String cardId, @RequestBody CardTransactionDTO transactionDTO) {
        cardService.processTransaction(transactionDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable String cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardDTO> updateCard(@PathVariable String cardId, @RequestBody CardDTO updatedCard) {
        CardDTO updated = cardService.updateCard(cardId, updatedCard);
        return ResponseEntity.ok(updated);
    }
}
