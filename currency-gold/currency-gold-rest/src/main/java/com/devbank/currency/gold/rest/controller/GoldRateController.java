package com.devbank.currency.gold.rest.controller;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.service.GoldRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gold-rates")
public class GoldRateController {

    private final GoldRateService goldRateService;

    public GoldRateController(GoldRateService goldRateService) {
        this.goldRateService = goldRateService;
    }

    @GetMapping
    public ResponseEntity<List<GoldRateDTO>> getAllGoldRates() {
        return ResponseEntity.ok(goldRateService.getAllGoldRates());
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveGoldRates() {
        goldRateService.fetchAndSaveGoldRates();
        return ResponseEntity.status(HttpStatus.CREATED).body("Gold rates fetched and saved successfully.");
    }

}
