package com.devbank.currency.gold.rest.config;

import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import com.devbank.currency.gold.impl.mongo.repository.CurrencyRateRepository;
import com.devbank.currency.gold.impl.mongo.repository.GoldRateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CurrencyGoldDataLoader implements CommandLineRunner {

    private final CurrencyRateRepository currencyRateRepository;
    private final GoldRateRepository goldRateRepository;

    public CurrencyGoldDataLoader(CurrencyRateRepository currencyRateRepository, GoldRateRepository goldRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
        this.goldRateRepository = goldRateRepository;
    }

    @Override
    public void run(String... args) {
        if (currencyRateRepository.count() == 0) {
            CurrencyRateDocument currencyRate = new CurrencyRateDocument("123213", "FFFS", 27.34);
            currencyRateRepository.save(currencyRate);
            System.out.println("Currency rate initial data loaded.");
        }

        if (goldRateRepository.count() == 0) {
            GoldRateDocument goldRate = new GoldRateDocument(1L, GoldType.GRAM, BigDecimal.valueOf(1675.50), BigDecimal.valueOf(1680.00), LocalDateTime.now());
            goldRateRepository.save(goldRate);
            System.out.println("Gold rate initial data loaded.");
        }
    }

}
