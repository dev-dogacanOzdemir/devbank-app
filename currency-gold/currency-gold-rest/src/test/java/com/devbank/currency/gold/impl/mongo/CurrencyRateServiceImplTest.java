package com.devbank.currency.gold.impl.mongo;

import com.devbank.currency.gold.api.DTO.CurrencyConversionRequestDTO;
import com.devbank.currency.gold.api.DTO.CurrencyConversionResponseDTO;
import com.devbank.currency.gold.api.DTO.CurrencyRateDTO;
import com.devbank.currency.gold.api.enums.CurrencyCode;
import com.devbank.currency.gold.impl.mongo.client.CurrencyApiClient;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import com.devbank.currency.gold.impl.mongo.mapper.CurrencyRateMapper;
import com.devbank.currency.gold.impl.mongo.repository.CurrencyRateRepository;
import com.devbank.currency.gold.impl.mongo.service.CurrencyRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CurrencyRateServiceImplTest {
    private CurrencyRateServiceImpl currencyRateService;
    private CurrencyApiClient currencyApiClientMock;
    private CurrencyRateRepository currencyRateRepositoryMock;

    @BeforeEach
    public void setUp() {
        currencyApiClientMock = mock(CurrencyApiClient.class);
        currencyRateRepositoryMock = mock(CurrencyRateRepository.class);
        currencyRateService = new CurrencyRateServiceImpl(currencyApiClientMock, currencyRateRepositoryMock);
    }

    @Test
    public void testGetAllCurrencyRates_Success() {
        // Mock API response
        Map<String, BigDecimal> mockRates = Map.of("USD", BigDecimal.valueOf(1.0), "EUR", BigDecimal.valueOf(0.85));
        when(currencyApiClientMock.fetchCurrencyRates()).thenReturn(mockRates);

        // Call the method
        List<CurrencyRateDTO> result = currencyRateService.getAllCurrencyRates();

        // Verify
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(currencyApiClientMock, times(1)).fetchCurrencyRates();
    }

    @Test
    public void testGetAllCurrencyRates_Failure() {
        // Mock API failure
        when(currencyApiClientMock.fetchCurrencyRates()).thenThrow(new RuntimeException("API Error"));

        // Call the method and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyRateService.getAllCurrencyRates();
        });

        // Verify exception message
        assertEquals("Failed to get currency rates", exception.getMessage());
        verify(currencyApiClientMock, times(1)).fetchCurrencyRates();
    }


    @Test
    public void testSaveCurrencyRates_Success() {
        // Prepare test data
        List<CurrencyRateDTO> rates = List.of(
                new CurrencyRateDTO("USD", 1.0),
                new CurrencyRateDTO("EUR", 0.85)
        );

        // Call the method
        assertDoesNotThrow(() -> currencyRateService.saveCurrencyRates(rates));

        // Verify repository interaction
        verify(currencyRateRepositoryMock, times(1)).saveAll(anyList());
    }

    @Test
    public void testSaveCurrencyRates_Failure() {
        // Prepare test data
        List<CurrencyRateDTO> rates = List.of(
                new CurrencyRateDTO("USD", 1.0),
                new CurrencyRateDTO("EUR", 0.85)
        );

        // Mock repository failure
        doThrow(new RuntimeException("Database Error")).when(currencyRateRepositoryMock).saveAll(anyList());

        // Call the method and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyRateService.saveCurrencyRates(rates);
        });

        // Verify exception message
        assertEquals("Failed to save currency rates", exception.getMessage());
        verify(currencyRateRepositoryMock, times(1)).saveAll(anyList());
    }


    @Test
    public void testConvertCurrency_Success() {
        // Mock repository responses
        when(currencyRateRepositoryMock.findByCurrencyCode("USD"))
                .thenReturn(Optional.of(new CurrencyRateDocument(null, "USD", 1.0)));
        when(currencyRateRepositoryMock.findByCurrencyCode("EUR"))
                .thenReturn(Optional.of(new CurrencyRateDocument(null, "EUR", 0.85)));

        // Prepare request
        CurrencyConversionRequestDTO request = new CurrencyConversionRequestDTO("USD", "EUR", 100.0);

        // Call the method
        CurrencyConversionResponseDTO response = currencyRateService.convertCurrency(request);

        // Verify
        assertNotNull(response);
        assertEquals(85.0, response.getConvertedAmount());
        verify(currencyRateRepositoryMock, times(1)).findByCurrencyCode("USD");
        verify(currencyRateRepositoryMock, times(1)).findByCurrencyCode("EUR");

        // Success message
        System.out.println("testConvertCurrency_Success: Conversion completed successfully.");
    }

}
