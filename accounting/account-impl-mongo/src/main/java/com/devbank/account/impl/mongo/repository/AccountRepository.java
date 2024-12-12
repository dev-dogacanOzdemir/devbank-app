package com.devbank.account.impl.mongo.repository;

import com.devbank.account.impl.mongo.document.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface AccountRepository  extends MongoRepository<AccountDocument, String> {

    // Hesap numarasına göre hasapları getir
    Optional<AccountDocument> findByAccountNumber(String accountNumber);

    // Hesap var mı kontrol et
    boolean existsByAccountNumber(String accountNumber);

    // Sahip kullanıcı adına göre hesapları getir
    List<AccountDocument> findByOwnerUsername(String ownerUsername);

    // Ortak hesapları getir
    List<AccountDocument> findBySharedUsernamesContaining(String username);

    // Belirli bir hesabın sahibi olup olmadığını kontrol et
    Optional<AccountDocument> findByAccountNumberAndOwnerUsername(String accountNumber, String ownerUsername);
}
