package com.devbank.accounting.api.DTO;

import com.devbank.accounting.api.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String accountId;        // Hesap ID
    private String customerId;       // Hesap sahibi kullanıcı ID
    private AccountType accountType; // Hesap türü (CURRENT/SAVINGS)
    private Double balance;        // Bakiye
    private String uniqueAccountNumber; //IBAN numarası
    private Date createdAt;        // Açılış tarihi
    private Double interestRate;   // Vadeli hesaplar için faiz oranı
    private Date maturityDate;     // Vadeli hesaplar için vade bitiş tarihi


}
