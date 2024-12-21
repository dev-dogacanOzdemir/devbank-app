package com.devbank.currency.gold.impl.mongo.mapper;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import org.springframework.stereotype.Component;

@Component
public class GoldRateMapper {

    public GoldRateDTO toDTO(GoldRateDocument document) {
        return GoldRateDTO.builder()
                .id(document.getId())
                .goldType(document.getGoldType())
                .buyRate(document.getBuyRate())
                .sellRate(document.getSellRate())
                .updatedAt(document.getUpdatedAt())
                .build();
    }

    public GoldRateDocument toDocument(GoldRateDTO dto) {
        return GoldRateDocument.builder()
                .id(dto.getId())
                .goldType(dto.getGoldType())
                .buyRate(dto.getBuyRate())
                .sellRate(dto.getSellRate())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
