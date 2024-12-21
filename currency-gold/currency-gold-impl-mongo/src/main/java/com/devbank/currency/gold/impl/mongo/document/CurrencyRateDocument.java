package com.devbank.currency.gold.impl.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "currency_rates")
public class CurrencyRateDocument {
    @Id
    private String id;
    private String currencyCode; // Örneğin: "USD"
    private double rate;         // Döviz değeri
}