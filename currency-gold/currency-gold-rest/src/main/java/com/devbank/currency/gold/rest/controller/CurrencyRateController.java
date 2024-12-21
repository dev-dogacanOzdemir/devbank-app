package com.devbank.currency.gold.rest.controller;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.service.CurrencyRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency-rates")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyService) {
        this.currencyRateService = currencyService;
    }

    @GetMapping
    public List<CurrencyRateDTO> getAllCurrencyRates() {
        return currencyRateService.getAllCurrencyRates();
    }

    @PostMapping("/convert")
    public CurrencyConversionResponseDTO convertCurrency(@RequestBody CurrencyConversionRequestDTO request) {
        return currencyRateService.convertCurrency(request);
    }
}
