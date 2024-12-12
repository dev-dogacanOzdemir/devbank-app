package com.devbank.user.management.impl.mongo.repository;

import com.devbank.user.management.impl.mongo.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByUsername(String username);
}
