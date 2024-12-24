package com.devbank.currency.gold.impl.mongo.client;

import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyApiClient {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/2470887815bae03aaed2d2b6/latest/USD";
    private final RestTemplate restTemplate;

    public CurrencyApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, BigDecimal> fetchCurrencyRates() {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

        // JSON'dan "conversion_rates" anahtarını al
        Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");

        // Tür uyumluluğunu kontrol et ve dönüştür
        Map<String, BigDecimal> convertedRates = new HashMap<>();
        rates.forEach((key, value) -> {
            // Hem Integer hem de Double türlerini ele al
            if (value instanceof Integer) {
                convertedRates.put(key, BigDecimal.valueOf((Integer) value));
            } else if (value instanceof Double) {
                convertedRates.put(key, BigDecimal.valueOf((Double) value));
            } else {
                throw new RuntimeException("Beklenmeyen tür: " + value.getClass());
            }
        });
        return convertedRates;
    }
}
