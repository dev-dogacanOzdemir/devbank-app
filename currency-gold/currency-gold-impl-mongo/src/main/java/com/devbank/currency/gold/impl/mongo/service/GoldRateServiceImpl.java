package com.devbank.currency.gold.impl.mongo.service;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.api.service.GoldRateService;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import com.devbank.currency.gold.impl.mongo.mapper.GoldRateMapper;
import com.devbank.currency.gold.impl.mongo.repository.GoldRateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoldRateServiceImpl implements GoldRateService {

    private final GoldRateRepository goldRateRepository;
    private final GoldRateMapper goldRateMapper;

    public GoldRateServiceImpl(GoldRateRepository goldRateRepository, GoldRateMapper goldRateMapper) {
        this.goldRateRepository = goldRateRepository;
        this.goldRateMapper = goldRateMapper;
    }

    @Override
    public GoldRateDTO getGoldRateDetails(GoldType goldType) {
        GoldRateDocument document = goldRateRepository.findAll()
                .stream()
                .filter(rate -> rate.getGoldType() == goldType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gold rate not found for type: " + goldType));
        return goldRateMapper.toDTO(document);
    }

    @Override
    public List<GoldRateDTO> getAllGoldRates() {
        return goldRateRepository.findAll()
                .stream()
                .map(goldRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GoldRateDTO updateGoldRate(GoldRateDTO goldRateDTO) {
        GoldRateDocument document = goldRateMapper.toDocument(goldRateDTO);
        GoldRateDocument savedDocument = goldRateRepository.save(document);
        return goldRateMapper.toDTO(savedDocument);
    }
}


