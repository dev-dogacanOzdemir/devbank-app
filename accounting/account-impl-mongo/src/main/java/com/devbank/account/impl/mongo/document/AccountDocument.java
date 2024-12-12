package com.devbank.account.impl.mongo.document;

import com.devbank.account.api.enums.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "accounts")
public class AccountDocument {
        @Id
    private String id;
    private String accountNumber;
    private String ownerUsername; // Hesap sahibinin kullanıcı adı
    private List<String> sharedUsernames; // Ortak kullanıcılar
    private double balance;
    private AccountType accountType;
}
