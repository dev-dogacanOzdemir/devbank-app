package com.devbank.currency.gold.api.DTO;

import com.devbank.currency.gold.api.enums.GoldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoldRateDTO {

    private GoldType goldType;
    private double sellPrice;
    private double buyPrice;
    private LocalDateTime updatedAt;

}
