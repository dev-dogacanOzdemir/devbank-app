package com.devbank.currency.gold.api.DTO;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDTO {

    private String currencyCode;
    private double rate;

}
