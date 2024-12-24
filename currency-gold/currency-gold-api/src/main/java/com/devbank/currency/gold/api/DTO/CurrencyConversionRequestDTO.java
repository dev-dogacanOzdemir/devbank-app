package com.devbank.currency.gold.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRequestDTO {

    private String sourceCurrency; // Kaynak para birimi
    private String targetCurrency; // Hedef para birimi
    private double amount;         // Dönüştürülecek miktar

}
