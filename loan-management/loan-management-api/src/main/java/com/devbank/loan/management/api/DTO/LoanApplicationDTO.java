package com.devbank.loan.management.api.DTO;


import com.devbank.loan.management.api.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {

    private String customerId;
    private double amount;
    private int termInMonths;
    private double interestRate;
    private LoanType loanType;
}
