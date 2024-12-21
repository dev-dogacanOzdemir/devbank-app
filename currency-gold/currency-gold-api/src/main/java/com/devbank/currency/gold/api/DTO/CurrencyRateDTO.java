package com.devbank.currency.gold.api.DTO;

import com.devbank.currency.gold.api.enums.CurrencyCode;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDTO {

    private String currencyCode;
    private double rate;
}
