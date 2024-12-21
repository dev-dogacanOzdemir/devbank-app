package com.devbank.currency.gold.impl.mongo.repository;

import com.devbank.currency.gold.api.enums.CurrencyCode;
import com.devbank.currency.gold.impl.mongo.document.CurrencyRateDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends MongoRepository<CurrencyRateDocument, String> {

    Optional<CurrencyRateDocument> findByCurrencyCode(String currencyCode);

}
