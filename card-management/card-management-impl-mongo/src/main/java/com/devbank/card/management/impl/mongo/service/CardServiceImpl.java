package com.devbank.card.management.impl.mongo.service;

import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.card.management.api.DTO.CardDTO;
import com.devbank.card.management.api.DTO.CardTransactionDTO;
import com.devbank.card.management.api.enums.CardStatus;
import com.devbank.card.management.api.enums.CardType;
import com.devbank.card.management.api.service.CardService;
import com.devbank.card.management.impl.mongo.document.CardDocument;
import com.devbank.card.management.impl.mongo.document.CardTransactionDocument;
import com.devbank.card.management.impl.mongo.document.CardTransactionLogDocument;
import com.devbank.card.management.impl.mongo.mapper.CardMapper;
import com.devbank.card.management.impl.mongo.mapper.CardTransactionMapper;
import com.devbank.card.management.impl.mongo.repository.CardRepository;
import com.devbank.card.management.impl.mongo.repository.CardTransactionLogRepository;
import com.devbank.card.management.impl.mongo.repository.CardTransactionRepository;
import com.devbank.error.management.exception.CardNotFoundException;
import com.devbank.error.management.exception.InsufficientBalanceException;
import com.devbank.error.management.exception.LimitExceededException;
import com.devbank.error.management.exception.TransactionNotAllowedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardTransactionRepository transactionRepository;
    private final CardMapper cardMapper;
    private final CardTransactionMapper transactionMapper;
    private final CardTransactionLogRepository transactionLogRepository;
    private final AccountService accountService;

    public CardServiceImpl(CardRepository cardRepository, CardTransactionRepository transactionRepository,
                           CardMapper cardMapper, CardTransactionMapper transactionMapper, CardTransactionLogRepository transactionLogRepository, AccountService accountService) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.cardMapper = cardMapper;
        this.transactionMapper = transactionMapper;
        this.transactionLogRepository = transactionLogRepository;
        this.accountService = accountService;
    }

    @Override
    public CardDTO createCard(CardDTO cardDTO) {
        CardDocument document = cardMapper.toDocument(cardDTO);
        return cardMapper.toDTO(cardRepository.save(document));
    }

    @Override
    public CardDTO getCardById(String cardId) {
        return cardRepository.findById(cardId).map(cardMapper::toDTO).orElseThrow(() -> new CardNotFoundException("Card not found: " + cardId));
    }

    @Override
    public List<CardDTO> getCardsByUserId(String userId) {
        return cardRepository.findByUserId(userId).stream().map(cardMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CardDTO updateCardStatus(String cardId, CardStatus status) {
        return cardRepository.findById(cardId).map(card -> {
            card.setStatus(status);
            return cardMapper.toDTO(cardRepository.save(card));
        }).orElseThrow(() -> new CardNotFoundException("Card not found: " + cardId));
    }

    @Override
    public void processTransaction(CardTransactionDTO transactionDTO) {
        CardDocument card = cardRepository.findById(transactionDTO.getCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found: " + transactionDTO.getCardId()));

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new TransactionNotAllowedException("Transaction not allowed: Card is not active");
        }

        if (card.getCardType() == CardType.DEBIT_CARD) {
            // 🏦 DEBIT CARD işlemi -> Hesap bakiyesinden düş
            accountService.withdrawFromAccount(card.getAccountId(), transactionDTO.getAmount());
        } else {
            // 💳 CREDIT CARD işlemi -> Kartın borcunu artır
            card.setBalance(card.getBalance() + transactionDTO.getAmount());
            cardRepository.save(card);
        }

        // 🔹 İşlem logunu kaydet
        CardTransactionLogDocument log = new CardTransactionLogDocument(
                UUID.randomUUID().toString(),
                transactionDTO.getTransactionId(),
                transactionDTO.getCardId(),
                card.getAccountId(),
                transactionDTO.getAmount(),
                transactionDTO.getTransactionType(),
                transactionDTO.getTransactionDate(),
                new Date()
        );
        transactionLogRepository.save(log);  // 🔹 Log kaydı MongoDB'ye ekleniyor

        transactionRepository.save(transactionMapper.toDocument(transactionDTO));
    }

    @Override
    public CardDTO updateCardLimit(String cardId, Double newLimit) {
        return cardRepository.findById(cardId).map(card -> {
            if (newLimit > 1000000) { // Örnek üst limit
                throw new LimitExceededException("New limit exceeds allowed maximum");
            }
            card.setCreditLimit(newLimit);
            return cardMapper.toDTO(cardRepository.save(card));
        }).orElseThrow(() -> new CardNotFoundException("Card not found: " + cardId));
    }
}
