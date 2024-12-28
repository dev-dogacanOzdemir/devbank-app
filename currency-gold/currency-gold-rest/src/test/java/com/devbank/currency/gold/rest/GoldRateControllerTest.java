package com.devbank.currency.gold.rest;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.api.service.GoldRateService;
import com.devbank.currency.gold.rest.controller.GoldRateController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled //Postman ile kontrol ediliyor.
@WebMvcTest(GoldRateController.class)
public class GoldRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoldRateService goldRateService;

    @Test
    public void testGetAllGoldRates_Success() throws Exception {

        List<GoldRateDTO> mockRates = List.of(
                new GoldRateDTO(GoldType.GRAM, 1680.0, 1675.5, LocalDateTime.now()),
                new GoldRateDTO(GoldType.QUARTER, 4200.0, 4100.0, LocalDateTime.now())
        );
        when(goldRateService.getAllGoldRates()).thenReturn(mockRates);


        mockMvc.perform(get("/api/gold-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].goldType").value("GRAM"))
                .andExpect(jsonPath("$[0].sellPrice").value(1680.0))
                .andExpect(jsonPath("$[1].goldType").value("QUARTER"));


        verify(goldRateService, times(1)).getAllGoldRates();
    }

    @Test
    public void testGetAllGoldRates_Failure() throws Exception {

        when(goldRateService.getAllGoldRates()).thenThrow(new RuntimeException("Database error"));


        mockMvc.perform(get("/api/gold-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));

        verify(goldRateService, times(1)).getAllGoldRates();
    }

    @Test
    public void testFetchAndSaveGoldRates_Success() throws Exception {

        doNothing().when(goldRateService).fetchAndSaveGoldRates();


        mockMvc.perform(post("/api/gold-rates/fetch")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Gold rates fetched and saved successfully."));


        verify(goldRateService, times(1)).fetchAndSaveGoldRates();
    }

    @Test
    public void testFetchAndSaveGoldRates_Failure() throws Exception {

        doThrow(new RuntimeException("API error")).when(goldRateService).fetchAndSaveGoldRates();


        mockMvc.perform(post("/api/gold-rates/fetch")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to fetch and save gold rates: API error"));


        verify(goldRateService, times(1)).fetchAndSaveGoldRates();
    }

}
