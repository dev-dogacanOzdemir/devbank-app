package com.devbank.currency.gold.impl.mongo.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@Component
public class GoldApiClient {

    @Value("${collectapi.apiKey}")
    private String apiKey;

    @Value("${collectapi.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(GoldApiClient.class);

    public GoldApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> fetchGoldPrices() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "apikey " + apiKey);
        headers.set("Content-Type", "application/json");

        log.info("Authorization: {}", "apikey " + apiKey);
        log.info("Request URL: {}", apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, Map.class);
        return (List<Map<String, Object>>) response.getBody().get("result");
    }
}
