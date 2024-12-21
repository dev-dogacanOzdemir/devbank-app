package com.devbank.currency.gold.rest.controller;
import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.api.service.GoldRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gold")
public class GoldRateController {

    private final GoldRateService goldRateService;

    public GoldRateController(GoldRateService goldRateService) {
        this.goldRateService = goldRateService;
    }

    @GetMapping
    public ResponseEntity<List<GoldRateDTO>> getAllGoldRates() {
        return ResponseEntity.ok(goldRateService.getAllGoldRates());
    }

    @GetMapping("/{goldType}")
    public ResponseEntity<GoldRateDTO> getGoldRateDetails(@PathVariable GoldType goldType) {
        return ResponseEntity.ok(goldRateService.getGoldRateDetails(goldType));
    }

    @PutMapping
    public ResponseEntity<GoldRateDTO> updateGoldRate(@RequestBody GoldRateDTO goldRateDTO) {
        return ResponseEntity.ok(goldRateService.updateGoldRate(goldRateDTO));
    }

}
