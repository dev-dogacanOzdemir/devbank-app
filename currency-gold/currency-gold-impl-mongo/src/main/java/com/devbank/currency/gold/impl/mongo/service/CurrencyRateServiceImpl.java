package com.devbank.currency.gold.impl.mongo.service;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.service.CurrencyRateService;
import com.devbank.currency.gold.impl.mongo.client.CurrencyApiClient;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import com.devbank.currency.gold.impl.mongo.repository.CurrencyRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyRateServiceImpl.class);

    private final CurrencyApiClient currencyApiClient;
    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRateServiceImpl(CurrencyApiClient currencyApiClient, CurrencyRateRepository currencyRateRepository) {
        this.currencyApiClient = currencyApiClient;
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public List<CurrencyRateDTO> getAllCurrencyRates() {
        try {
            Map<String, BigDecimal> rates = currencyApiClient.fetchCurrencyRates();
            saveCurrencyRates(rates.entrySet().stream()
                    .map(entry -> new CurrencyRateDTO(entry.getKey(), entry.getValue().doubleValue()))
                    .collect(Collectors.toList()));
            return rates.entrySet().stream()
                    .map(entry -> new CurrencyRateDTO(entry.getKey(), entry.getValue().doubleValue()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching or saving currency rates: {}", e.getMessage());
            throw new RuntimeException("Failed to get currency rates", e);
        }
    }

    @Override
    public CurrencyConversionResponseDTO convertCurrency(CurrencyConversionRequestDTO request) {

        if(request.getAmount() <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        // Kaynak ve hedef oranları getir
        double sourceRate = currencyRateRepository.findByCurrencyCode(request.getSourceCurrency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Source currency not found -> " + request.getSourceCurrency()))
                .getRate();
        double targetRate = currencyRateRepository.findByCurrencyCode(request.getTargetCurrency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target currency not found -> " + request.getTargetCurrency()))
                .getRate();

        // Dönüşüm işlemi
        double rate = targetRate / sourceRate;
        double convertedAmount = request.getAmount() * rate;
        System.out.println("Target Rate : " + targetRate);
        System.out.println("Source Rate : " + sourceRate);
        return new CurrencyConversionResponseDTO(convertedAmount);
    }

    @Override
    public void saveCurrencyRates(List<CurrencyRateDTO> rates) {
        try {
            // Veritabanındaki mevcut oranları al
            List<String> existingCodes = currencyRateRepository.findAll()
                    .stream()
                    .map(CurrencyRateDocument::getCurrencyCode)
                    .collect(Collectors.toList());

            // Yeni oranlardan zaten mevcut olanları çıkar
            List<CurrencyRateDocument> newRates = rates.stream()
                    .filter(rate -> !existingCodes.contains(rate.getCurrencyCode()))
                    .map(rate -> new CurrencyRateDocument(null, rate.getCurrencyCode(), rate.getRate()))
                    .collect(Collectors.toList());

            if (!newRates.isEmpty()) {
                currencyRateRepository.saveAll(newRates);
                log.info("New currency rates saved: {}", newRates);
            } else {
                log.info("No new currency rates to save.");
            }
        } catch (Exception e) {
            log.error("Error saving currency rates: {}", e.getMessage());
            throw new RuntimeException("Failed to save currency rates", e);
        }
    }

}
