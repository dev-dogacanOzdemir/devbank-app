package com.devbank.user.management.impl.mongo.repository;

import com.devbank.user.management.impl.mongo.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, Long> {

    // T.C. kimlik numarasına göre kullanıcı bulma
    Optional<UserDocument> findByTcNumber(String tcNumber);

    // T.C. kimlik numarası ve telefon numarasına göre kullanıcı bulma
    Optional<UserDocument> findByTcNumberAndPhoneNumber(String tcNumber, String phoneNumber);

    Optional<UserDocument> findById(String id);
}
