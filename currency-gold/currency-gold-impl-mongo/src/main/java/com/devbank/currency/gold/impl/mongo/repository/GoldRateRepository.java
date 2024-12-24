package com.devbank.currency.gold.impl.mongo.repository;

import com.devbank.currency.gold.api.enums.GoldType;
import com.devbank.currency.gold.impl.mongo.document.GoldRateDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoldRateRepository extends MongoRepository<GoldRateDocument,Long> {
    Optional<GoldRateDocument> findByGoldType(GoldType goldType);
}
