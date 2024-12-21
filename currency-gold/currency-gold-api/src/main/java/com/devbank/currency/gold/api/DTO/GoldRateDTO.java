package com.devbank.currency.gold.api.DTO;

import com.devbank.currency.gold.api.enums.GoldType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoldRateDTO {

    @NotNull
    private Long id;

    @NotNull
    private GoldType goldType;

    @NotNull
    private BigDecimal buyRate;

    @NotNull
    private BigDecimal sellRate;

    @NotNull
    private LocalDateTime updatedAt;
}
