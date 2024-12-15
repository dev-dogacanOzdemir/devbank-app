package com.devbank.user.management.impl.mongo.repository;

import com.devbank.user.management.impl.mongo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    // T.C. kimlik numarasına göre kullanıcı bulma
    Optional<User> findByTcNumber(String tcNumber);

    // T.C. kimlik numarası ve telefon numarasına göre kullanıcı bulma
    Optional<User> findByTcNumberAndPhoneNumber(String tcNumber, String phoneNumber);

    Optional<User> findById(Long id);
}
