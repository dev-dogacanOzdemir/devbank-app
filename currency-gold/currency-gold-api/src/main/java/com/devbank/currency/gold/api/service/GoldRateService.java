package com.devbank.currency.gold.api.service;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;

import java.util.List;

public interface GoldRateService {

    GoldRateDTO getGoldRateDetails(GoldType goldType);

    List<GoldRateDTO> getAllGoldRates();

    GoldRateDTO updateGoldRate(GoldRateDTO goldRateDTO);
}
