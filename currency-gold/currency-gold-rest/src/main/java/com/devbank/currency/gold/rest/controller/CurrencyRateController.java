package com.devbank.currency.gold.rest.controller;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.service.CurrencyRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyService) {
        this.currencyRateService = currencyService;
    }

    // Tüm döviz kurlarını getir
    @GetMapping
    public ResponseEntity<List<CurrencyRateDTO>> getAllCurrencyRates() {
        try {
            List<CurrencyRateDTO> rates = currencyRateService.getAllCurrencyRates();
            return ResponseEntity.ok(rates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Döviz oranlarını API'den çekip kaydet
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveCurrencyRates() {
        try {
            // API'den kurları çek ve kaydet
            List<CurrencyRateDTO> rates = currencyRateService.getAllCurrencyRates();
            currencyRateService.saveCurrencyRates(rates);

            // Başarı yanıtı
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Currency rates fetched and saved successfully.");
        } catch (Exception e) {
            // Hata yanıtı
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch and save currency rates: " + e.getMessage());
        }
    }

    // Döviz dönüşümü yap
    @PostMapping("/convert")
    public ResponseEntity<CurrencyConversionResponseDTO> convertCurrency(@RequestBody CurrencyConversionRequestDTO request) {
        try {
            CurrencyConversionResponseDTO response = currencyRateService.convertCurrency(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
