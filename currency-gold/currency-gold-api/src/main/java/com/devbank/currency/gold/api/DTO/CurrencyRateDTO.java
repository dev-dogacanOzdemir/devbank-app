package com.devbank.currency.gold.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDTO {

    private String currencyCode;
    private double rate;

}
