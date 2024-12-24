package com.devbank.accounting.impl.mongo.document;


import com.devbank.accounting.api.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accounts")
public class AccountDocument {

    @Id
    private Long accountId;
    private Long customerId;
    private AccountType accountType;
    private Double balance;
    private Date createdAt;
    private Double interestRate; // Vadeli hesaplar için faiz oranı
    private Date maturityDate;   // Vadeli hesaplar için vade bitiş tarihi
}
