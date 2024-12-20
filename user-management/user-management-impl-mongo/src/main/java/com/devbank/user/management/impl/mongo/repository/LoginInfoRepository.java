package com.devbank.user.management.impl.mongo.repository;

import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoginInfoRepository extends MongoRepository<LoginInfoDocument, Long> {

    List<LoginInfoDocument> findByUserId(Long userId);
}
