package com.devbank.currency.gold.api.service;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;

import java.util.List;

public interface CurrencyRateService {
    List<CurrencyRateDTO> getAllCurrencyRates();
    void saveCurrencyRates(List<CurrencyRateDTO> rates);
    CurrencyConversionResponseDTO convertCurrency(CurrencyConversionRequestDTO request);
}
