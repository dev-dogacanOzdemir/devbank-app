package com.devbank.currency.gold.api.service;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;

import java.util.List;

public interface GoldRateService {

    List<GoldRateDTO> getAllGoldRates();

    void fetchAndSaveGoldRates();

    void saveGoldRates(List<GoldRateDTO> rates);

}
