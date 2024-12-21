package com.devbank.currency.gold.impl.mongo.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateApiResponse {

    private String result;
    private String baseCode;
    private Map<String, Double> conversionRates;
}
