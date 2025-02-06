package com.devbank.loan.management.api.service;

import com.devbank.loan.management.api.DTO.LoanApplicationDTO;
import com.devbank.loan.management.api.DTO.LoanPaymentDTO;
import com.devbank.loan.management.api.DTO.LoanResponseDTO;

import java.util.List;

public interface LoanService {
    LoanResponseDTO applyForLoan(LoanApplicationDTO loanApplicationDTO);
    LoanResponseDTO approveLoan(String loanId);
    LoanResponseDTO rejectLoan(String loanId);
    List<LoanResponseDTO> getCustomerLoans(String customerId);
    List<LoanResponseDTO> getPendingLoans();
    LoanResponseDTO getLoanById(String loanId);
    void payLoan(String loanId, LoanPaymentDTO paymentDTO);
    List<LoanPaymentDTO> getPaymentHistory(String loanId);
    List<LoanResponseDTO> getAllLoans();
    LoanResponseDTO updateLoan(String loanId, LoanResponseDTO updatedLoan);
}
