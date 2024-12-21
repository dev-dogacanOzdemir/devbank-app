package com.devbank.currency.gold.impl.mongo.repository;

import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoldRateRepository extends MongoRepository<GoldRateDocument,Long> {
}
