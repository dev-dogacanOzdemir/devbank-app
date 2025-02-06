package com.devbank.loan.management.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanPaymentDTO {

    private double paymentAmount;
    private LocalDateTime paymentDate;
}
