package com.devbank.loan.management.api.DTO;

import com.devbank.loan.management.api.enums.LoanStatus;
import com.devbank.loan.management.api.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {

    private String loanId;
    private String customerId;
    private double amount;
    private int termInMonths;
    private double interestRate;
    private LoanType loanType;
    private LoanStatus status;
    private String approvedBy;
    private LocalDateTime createdAt;

}
