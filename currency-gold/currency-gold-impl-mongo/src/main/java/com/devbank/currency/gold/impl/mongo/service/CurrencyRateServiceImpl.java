package com.devbank.currency.gold.impl.mongo.service;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.service.CurrencyRateService;
import com.devbank.currency.gold.impl.mongo.client.CurrencyApiClient;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import com.devbank.currency.gold.impl.mongo.repository.CurrencyRateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyApiClient currencyApiClient;
    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRateServiceImpl(CurrencyApiClient currencyApiClient, CurrencyRateRepository currencyRateRepository) {
        this.currencyApiClient = currencyApiClient;
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public List<CurrencyRateDTO> getAllCurrencyRates() {
        // API'den kurları çek
        Map<String, BigDecimal> rates = currencyApiClient.fetchCurrencyRates();
        saveCurrencyRates(rates);
        // DTO'ya dönüştür
        return rates.entrySet().stream()
                .map(entry -> new CurrencyRateDTO(entry.getKey(), entry.getValue().doubleValue()))
                .collect(Collectors.toList());
    }

    @Override
    public CurrencyConversionResponseDTO convertCurrency(CurrencyConversionRequestDTO request) {

        if(request.getAmount() <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        // Kaynak ve hedef oranları getir
        double sourceRate = currencyRateRepository.findByCurrencyCode(request.getSourceCurrency())
                .orElseThrow(() -> new RuntimeException("Source currency not found -> " + request.getSourceCurrency()))
                .getRate();
        double targetRate = currencyRateRepository.findByCurrencyCode(request.getTargetCurrency())
                .orElseThrow(() -> new RuntimeException("Target currency not found -> " + request.getTargetCurrency()))
                .getRate();

        // Dönüşüm işlemi
        double convertedAmount = (request.getAmount() / sourceRate) * targetRate;
        return new CurrencyConversionResponseDTO(convertedAmount);
    }

    @Override
    public void saveCurrencyRates(List<CurrencyRateDTO> rates) {
        currencyRateRepository.saveAll(rates); // Veritabanına kaydet
    }

}
