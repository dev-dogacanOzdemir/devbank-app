package com.devbank.currency.gold.impl.mongo.service;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.api.service.GoldRateService;
import com.devbank.currency.gold.impl.mongo.client.GoldApiClient;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import com.devbank.currency.gold.impl.mongo.repository.GoldRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoldRateServiceImpl implements GoldRateService {

    private static final Logger log = LoggerFactory.getLogger(GoldRateServiceImpl.class);

    private final GoldApiClient goldApiClient;
    private final GoldRateRepository goldRateRepository;

    public GoldRateServiceImpl(GoldApiClient goldApiClient, GoldRateRepository goldRateRepository) {
        this.goldApiClient = goldApiClient;
        this.goldRateRepository = goldRateRepository;
    }

    @Override
    public List<GoldRateDTO> getAllGoldRates() {
        return goldRateRepository.findAll().stream()
                .map(doc -> new GoldRateDTO(doc.getGoldType(), doc.getSellPrice(), doc.getBuyPrice(), doc.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public void fetchAndSaveGoldRates() {
        try {
            List<Map<String, Object>> apiResults = goldApiClient.fetchGoldPrices();

            // API sonuçlarını kaydet
            List<GoldRateDTO> rates = apiResults.stream()
                    .map(result -> {
                        GoldType goldType = GoldType.fromApiName(result.get("name").toString());
                        if (goldType != null) {
                            double buyPrice = Double.parseDouble(result.get("buying").toString());
                            double sellPrice = Double.parseDouble(result.get("selling").toString());
                            return new GoldRateDTO(goldType, sellPrice, buyPrice, LocalDateTime.now());
                        }
                        return null;
                    })
                    .filter(dto -> dto != null)
                    .collect(Collectors.toList());

            saveGoldRates(rates);
        } catch (Exception e) {
            log.error("Error fetching and saving gold rates: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch and save gold rates", e);
        }
    }

    @Override
    public void saveGoldRates(List<GoldRateDTO> rates) {
        try {
            // Veritabanındaki mevcut oranları al
            List<GoldType> existingTypes = goldRateRepository.findAll().stream()
                    .map(GoldRateDocument::getGoldType)
                    .collect(Collectors.toList());

            // Yeni oranlardan zaten mevcut olanları çıkar
            List<GoldRateDocument> newRates = rates.stream()
                    .filter(rate -> !existingTypes.contains(rate.getGoldType()))
                    .map(rate -> new GoldRateDocument(null, rate.getGoldType(), rate.getSellPrice(), rate.getBuyPrice(), rate.getUpdatedAt()))
                    .collect(Collectors.toList());

            if (!newRates.isEmpty()) {
                goldRateRepository.saveAll(newRates);
                log.info("New gold rates saved: {}", newRates);
            } else {
                log.info("No new gold rates to save.");
            }
        } catch (Exception e) {
            log.error("Error saving gold rates: {}", e.getMessage());
            throw new RuntimeException("Failed to save gold rates", e);
        }
    }
}



