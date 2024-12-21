package com.devbank.currency.gold.impl.mongo.document;

import com.devbank.currency.gold.api.enums.GoldType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "gold_rates")
public class GoldRateDocument {

    @Id
    private Long id;

    private GoldType goldType;

    private BigDecimal buyRate;

    private BigDecimal sellRate;

    private LocalDateTime updatedAt;
}
