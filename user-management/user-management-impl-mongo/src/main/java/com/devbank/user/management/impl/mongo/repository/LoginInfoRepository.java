package com.devbank.user.management.impl.mongo.repository;

import com.devbank.user.management.impl.mongo.model.LoginInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoginInfoRepository extends MongoRepository<LoginInfo, Long> {

    List<LoginInfo> findByUserId(Long userId);
}
