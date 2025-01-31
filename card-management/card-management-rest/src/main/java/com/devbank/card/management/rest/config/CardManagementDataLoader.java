package com.devbank.card.management.rest.config;

import com.devbank.card.management.api.enums.CardStatus;
import com.devbank.card.management.api.enums.CardType;
import com.devbank.card.management.impl.mongo.document.CardDocument;
import com.devbank.card.management.impl.mongo.repository.CardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class CardManagementDataLoader {

    @Bean
    CommandLineRunner initCardDatabase(CardRepository cardRepository) {
        return args -> {
            if (cardRepository.count() == 0) {
                CardDocument debitCard1 = new CardDocument(
                        "card1001",
                        "12345",
                        "1",  // Banka kartı için hesap ID zorunlu
                        "1111222233334444",
                        CardType.DEBIT_CARD,
                        CardStatus.ACTIVE,
                        0.0,
                        3000.0,  // Banka kartı için hesap bakiyesi
                        new Date()
                );

                CardDocument debitCard2 = new CardDocument(
                        "card1002",
                        "67890",
                        "3",  // Banka kartı için hesap ID zorunlu
                        "5555666677778888",
                        CardType.DEBIT_CARD,
                        CardStatus.ACTIVE,
                        0.0,
                        30000.0,  // Banka kartı için hesap bakiyesi
                        new Date()
                );

                CardDocument creditCard = new CardDocument(
                        "card2001",
                        "67890",
                        null,  // Kredi kartları için hesap ID zorunlu değil
                        "9999000011112222",
                        CardType.CREDIT_CARD,
                        CardStatus.ACTIVE,
                        10000.0,  // Kredi kartı limiti
                        0.0,  // Kredi kartı başlangıç bakiyesi (Borç yok)
                        new Date()
                );

                cardRepository.saveAll(List.of(debitCard1, debitCard2, creditCard));
                System.out.println("Sample card data loaded into database.");
            }
        };
    }
}
