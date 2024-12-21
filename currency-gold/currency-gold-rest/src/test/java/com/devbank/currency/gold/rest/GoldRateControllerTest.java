package com.devbank.currency.gold.rest;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.api.service.GoldRateService;
import com.devbank.currency.gold.rest.controller.GoldRateController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GoldRateController.class)
public class GoldRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoldRateService goldRateService;

    @Test
    void testGetAllGoldRates() throws Exception {
        List<GoldRateDTO> rates = List.of(
                new GoldRateDTO(1L, GoldType.GRAM, BigDecimal.valueOf(1500.0), BigDecimal.valueOf(1510.0), LocalDateTime.now()),
                new GoldRateDTO(2L, GoldType.OUNCE, BigDecimal.valueOf(48000.0), BigDecimal.valueOf(48100.0), LocalDateTime.now())
        );

        when(goldRateService.getAllGoldRates()).thenReturn(rates);

        mockMvc.perform(get("/api/gold"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetGoldRateDetails() throws Exception {
        GoldRateDTO rate = new GoldRateDTO(1L, GoldType.GRAM, BigDecimal.valueOf(1500.0), BigDecimal.valueOf(1510.0), LocalDateTime.now());

        when(goldRateService.getGoldRateDetails(GoldType.GRAM)).thenReturn(rate);

        mockMvc.perform(get("/api/gold/GRAM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goldType").value("GRAM"));
    }

    @Test
    void testUpdateGoldRate() throws Exception {
        GoldRateDTO rate = new GoldRateDTO(1L, GoldType.GRAM, BigDecimal.valueOf(1500.0), BigDecimal.valueOf(1510.0), LocalDateTime.now());

        when(goldRateService.updateGoldRate(Mockito.any(GoldRateDTO.class))).thenReturn(rate);

        String json = """
                {
                    "id": 1,
                    "goldType": "GRAM",
                    "buyRate": 1500.0,
                    "sellRate": 1510.0,
                    "updatedAt": "2024-12-21T12:00:00"
                }
                """;

        mockMvc.perform(put("/api/gold")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goldType").value("GRAM"));
    }
}
