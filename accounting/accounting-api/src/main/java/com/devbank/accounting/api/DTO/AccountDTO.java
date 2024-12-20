package com.devbank.accounting.api.DTO;

import com.devbank.accounting.api.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @NotNull(message = "Account ID cannot be null.")
    private Long accountId;        // Hesap ID
    @NotNull(message = "Customer ID cannot be null.")
    private Long customerId;       // Hesap sahibi kullanıcı ID
    @NotNull(message = "Account type cannot be null.")
    private AccountType accountType; // Hesap türü (CURRENT/SAVINGS)
    @NotNull(message = "Balance cannot be null.")
    private Double balance;        // Bakiye
    @NotNull(message = "Create date cannot be null.")
    private Date createdAt;        // Açılış tarihi
    private Double interestRate;   // Vadeli hesaplar için faiz oranı
    private Date maturityDate;     // Vadeli hesaplar için vade bitiş tarihi
}
