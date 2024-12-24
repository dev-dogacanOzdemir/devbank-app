package com.devbank.currency.gold.rest;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.service.CurrencyRateService;
import com.devbank.currency.gold.rest.controller.CurrencyRateController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled //Postman ile kontrol ediliyor.
@WebMvcTest(CurrencyRateController.class)
public class CurrencyRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRateService currencyRateService;

    @Test
    public void testGetAllCurrencyRates() throws Exception {

        List<CurrencyRateDTO> mockRates = List.of(
                new CurrencyRateDTO("USD", 1.0),
                new CurrencyRateDTO("EUR", 0.85)
        );
        when(currencyRateService.getAllCurrencyRates()).thenReturn(mockRates);


        mockMvc.perform(get("/api/currency-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].currencyCode").value("USD"))
                .andExpect(jsonPath("$[1].currencyCode").value("EUR"));


        verify(currencyRateService, times(1)).getAllCurrencyRates();
    }

    @Test
    public void testFetchAndSaveCurrencyRates() throws Exception {

        doNothing().when(currencyRateService).saveCurrencyRates(anyList());
        when(currencyRateService.getAllCurrencyRates()).thenReturn(List.of());


        mockMvc.perform(post("/api/currency-rates/fetch"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Currency rates fetched and saved successfully."));


        verify(currencyRateService, times(1)).getAllCurrencyRates();
        verify(currencyRateService, times(1)).saveCurrencyRates(anyList());
    }

    @Test
    public void testConvertCurrency() throws Exception {

        CurrencyConversionRequestDTO request = new CurrencyConversionRequestDTO("USD", "EUR", 100.0);
        CurrencyConversionResponseDTO response = new CurrencyConversionResponseDTO(85.0);
        when(currencyRateService.convertCurrency(any())).thenReturn(response);


        mockMvc.perform(post("/api/currency-rates/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.convertedAmount").value(85.0));


        verify(currencyRateService, times(1)).convertCurrency(any(CurrencyConversionRequestDTO.class));
    }

    @Test
    public void testFetchAndSaveCurrencyRates_Failure() throws Exception {

        when(currencyRateService.getAllCurrencyRates()).thenThrow(new RuntimeException("API Error"));


        mockMvc.perform(post("/api/currency-rates/fetch"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to fetch and save currency rates: API Error"));


        verify(currencyRateService, times(1)).getAllCurrencyRates();
    }

    @Test
    public void testConvertCurrency_Failure() throws Exception {

        when(currencyRateService.convertCurrency(any())).thenThrow(new RuntimeException("Conversion Error"));


        mockMvc.perform(post("/api/currency-rates/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(status().isNotFound());


        verify(currencyRateService, times(1)).convertCurrency(any(CurrencyConversionRequestDTO.class));
    }
}
