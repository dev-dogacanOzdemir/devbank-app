package com.devbank.account.api.DTO;

import com.devbank.account.api.enums.AccountType;
import lombok.Data;

@Data
public class AccountDTO {

    private String accountNumber;
    private String customerName;
    private AccountType accountType;

}
