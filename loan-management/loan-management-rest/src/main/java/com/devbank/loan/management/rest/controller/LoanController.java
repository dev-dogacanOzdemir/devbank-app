package com.devbank.loan.management.rest.controller;

import com.devbank.loan.management.api.DTO.LoanApplicationDTO;
import com.devbank.loan.management.api.DTO.LoanPaymentDTO;
import com.devbank.loan.management.api.DTO.LoanResponseDTO;
import com.devbank.loan.management.api.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")

public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanResponseDTO> applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO) {
        return ResponseEntity.ok(loanService.applyForLoan(loanApplicationDTO));
    }

    @PutMapping("/{loanId}/approve")
    public ResponseEntity<LoanResponseDTO> approveLoan(@PathVariable String loanId) {
        return ResponseEntity.ok(loanService.approveLoan(loanId));
    }

    @PutMapping("/{loanId}/reject")
    public ResponseEntity<LoanResponseDTO> rejectLoan(@PathVariable String loanId) {
        return ResponseEntity.ok(loanService.rejectLoan(loanId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponseDTO>> getCustomerLoans(@PathVariable String customerId) {
        return ResponseEntity.ok(loanService.getCustomerLoans(customerId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LoanResponseDTO>> getPendingLoans() {
        return ResponseEntity.ok(loanService.getPendingLoans());
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable String loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @PostMapping("/{loanId}/pay")
    public ResponseEntity<String> payLoan(@PathVariable String loanId, @RequestBody LoanPaymentDTO paymentDTO) {
        loanService.payLoan(loanId, paymentDTO);
        return ResponseEntity.ok("Payment successful.");
    }

    @GetMapping("/{loanId}/payment-history")
    public ResponseEntity<List<LoanPaymentDTO>> getPaymentHistory(@PathVariable String loanId) {
        return ResponseEntity.ok(loanService.getPaymentHistory(loanId));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<LoanResponseDTO> updateLoan(@PathVariable String loanId, @RequestBody LoanResponseDTO updatedLoan) {
        LoanResponseDTO updated = loanService.updateLoan(loanId, updatedLoan);
        return ResponseEntity.ok(updated);
    }
}
