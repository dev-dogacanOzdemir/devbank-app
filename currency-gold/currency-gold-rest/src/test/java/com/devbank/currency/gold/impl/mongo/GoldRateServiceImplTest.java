package com.devbank.currency.gold.impl.mongo;

import com.devbank.currency.gold.api.DTO.GoldRateDTO;
import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.impl.mongo.client.GoldApiClient;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import com.devbank.currency.gold.impl.mongo.repository.GoldRateRepository;
import com.devbank.currency.gold.impl.mongo.service.GoldRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoldRateServiceImplTest {

    private GoldRateServiceImpl goldRateService;
    private GoldApiClient goldApiClientMock;
    private GoldRateRepository goldRateRepositoryMock;

    @BeforeEach
    public void setUp() {
        goldApiClientMock = mock(GoldApiClient.class);
        goldRateRepositoryMock = mock(GoldRateRepository.class);
        goldRateService = new GoldRateServiceImpl(goldApiClientMock, goldRateRepositoryMock);
    }

    @Test
    public void testGetAllGoldRates_Success() {
        // Mock repository response
        List<GoldRateDocument> mockDocuments = List.of(
                new GoldRateDocument(null, GoldType.GRAM, 1680.0, 1675.5, LocalDateTime.now()),
                new GoldRateDocument(null, GoldType.QUARTER, 4200.0, 4100.0, LocalDateTime.now())
        );
        when(goldRateRepositoryMock.findAll()).thenReturn(mockDocuments);

        // Call the method
        List<GoldRateDTO> result = goldRateService.getAllGoldRates();

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(GoldType.GRAM, result.get(0).getGoldType());
        assertEquals(1680.0, result.get(0).getSellPrice());
        verify(goldRateRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetAllGoldRates_Failure() {
        // Mock repository to throw an exception
        when(goldRateRepositoryMock.findAll()).thenThrow(new RuntimeException("Database error"));

        // Call the method and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            goldRateService.getAllGoldRates();
        });

        // Verify exception message
        assertEquals("Database error", exception.getMessage());
        verify(goldRateRepositoryMock, times(1)).findAll();
    }


    @Test
    public void testFetchAndSaveGoldRates_Success() {
        // Mock API response
        List<Map<String, Object>> apiResponse = List.of(
                Map.of("name", "Gram Altın", "buying", 1675.5, "selling", 1680.0),
                Map.of("name", "Çeyrek Altın", "buying", 4100.0, "selling", 4200.0)
        );
        when(goldApiClientMock.fetchGoldPrices()).thenReturn(apiResponse);

        // Call the method
        goldRateService.fetchAndSaveGoldRates();

        // Capture the saved documents
        ArgumentCaptor<List<GoldRateDocument>> captor = ArgumentCaptor.forClass(List.class);
        verify(goldRateRepositoryMock, times(2)).save(any(GoldRateDocument.class));
    }

    @Test
    public void testFetchAndSaveGoldRates_InvalidGoldType() {
        // Mock API response with an invalid gold type
        List<Map<String, Object>> apiResponse = List.of(
                Map.of("name", "Invalid Type", "buying", 1675.5, "selling", 1680.0)
        );
        when(goldApiClientMock.fetchGoldPrices()).thenReturn(apiResponse);

        // Call the method
        goldRateService.fetchAndSaveGoldRates();

        // Verify that no documents were saved
        verify(goldRateRepositoryMock, never()).save(any(GoldRateDocument.class));
    }

    @Test
    public void testFetchAndSaveGoldRates_DatabaseFailure() {
        // Mock API response
        List<Map<String, Object>> apiResponse = List.of(
                Map.of("name", "Gram Altın", "buying", 1675.5, "selling", 1680.0)
        );
        when(goldApiClientMock.fetchGoldPrices()).thenReturn(apiResponse);

        // Mock repository to throw an exception during save
        doThrow(new RuntimeException("Database error")).when(goldRateRepositoryMock).save(any(GoldRateDocument.class));

        // Call the method and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            goldRateService.fetchAndSaveGoldRates();
        });

        // Verify exception message
        assertEquals("Database error", exception.getMessage());
        verify(goldApiClientMock, times(1)).fetchGoldPrices();
        verify(goldRateRepositoryMock, times(1)).save(any(GoldRateDocument.class));
    }

    @Test
    public void testFetchAndSaveGoldRates_ApiFailure() {
        // Mock API client to throw an exception
        when(goldApiClientMock.fetchGoldPrices()).thenThrow(new RuntimeException("API error"));

        // Call the method and expect exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            goldRateService.fetchAndSaveGoldRates();
        });

        // Verify exception message
        assertEquals("API error", exception.getMessage());
        verify(goldApiClientMock, times(1)).fetchGoldPrices();
        verify(goldRateRepositoryMock, never()).save(any(GoldRateDocument.class));
    }

}
