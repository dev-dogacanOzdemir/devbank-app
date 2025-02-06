package com.devbank.loan.management.impl.mongo.service;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.api.enums.AccountType;
import com.devbank.accounting.api.service.AccountService;
import com.devbank.error.management.exception.InsufficientBalanceException;
import com.devbank.loan.management.api.DTO.LoanApplicationDTO;
import com.devbank.loan.management.api.DTO.LoanPaymentDTO;
import com.devbank.loan.management.api.DTO.LoanResponseDTO;
import com.devbank.loan.management.api.enums.LoanStatus;
import com.devbank.loan.management.api.service.LoanService;
import com.devbank.loan.management.impl.mongo.document.LoanDocument;
import com.devbank.loan.management.impl.mongo.document.LoanPaymentDocument;
import com.devbank.loan.management.impl.mongo.mapper.LoanMapper;
import com.devbank.loan.management.impl.mongo.mapper.LoanPaymentMapper;
import com.devbank.loan.management.impl.mongo.repository.LoanPaymentRepository;
import com.devbank.loan.management.impl.mongo.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanMapper loanMapper;
    private final LoanPaymentMapper loanPaymentMapper;
    private final AccountService accountService;

    public LoanServiceImpl(LoanRepository loanRepository,
                           LoanPaymentRepository loanPaymentRepository,
                           LoanMapper loanMapper,
                           LoanPaymentMapper loanPaymentMapper,
                           AccountService accountService) {
        this.loanRepository = loanRepository;
        this.loanPaymentRepository = loanPaymentRepository;
        this.loanMapper = loanMapper;
        this.loanPaymentMapper = loanPaymentMapper;
        this.accountService = accountService;
    }

    @Override
    public LoanResponseDTO applyForLoan(LoanApplicationDTO loanApplicationDTO) {
        LoanDocument loan = new LoanDocument(null, loanApplicationDTO.getCustomerId(), loanApplicationDTO.getAmount(),
                loanApplicationDTO.getTermInMonths(), loanApplicationDTO.getInterestRate(), loanApplicationDTO.getLoanType(),
                LoanStatus.PENDING, null, LocalDateTime.now(), null);
        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public LoanResponseDTO approveLoan(String loanId) {
        LoanDocument loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Eğer kredi zaten onaylanmışsa işlemi tekrarlama
        if (loan.getStatus() == LoanStatus.APPROVED) {
            throw new RuntimeException("Loan is already approved.");
        }

        // Müşterinin hesabını al
        AccountDTO customerAccount = accountService.getAccountById(loan.getCustomerId());

        // Vadeli hesap kontrolü
        if(customerAccount.getAccountType() == AccountType.SAVINGS){
            throw new RuntimeException("Saving account is not allowed to approve loan.");
        }

        // Krediyi müşteri hesabına aktar
        double newBalance = customerAccount.getBalance() + loan.getAmount();
        accountService.updateAccountBalance(customerAccount.getAccountId(), newBalance);

        // Kredi durumunu APPROVED olarak güncelle
        loan.setStatus(LoanStatus.APPROVED);
        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public LoanResponseDTO rejectLoan(String loanId) {
        LoanDocument loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus(LoanStatus.REJECTED);
        return loanMapper.toDTO(loanRepository.save(loan));
    }

    @Override
    public List<LoanResponseDTO> getCustomerLoans(String customerId) {
        return loanRepository.findByCustomerId(customerId).stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> getPendingLoans() {
        return loanRepository.findByStatus(LoanStatus.PENDING).stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponseDTO getLoanById(String loanId) {
        LoanDocument loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return loanMapper.toDTO(loan);
    }

    @Override
    public void payLoan(String loanId, LoanPaymentDTO paymentDTO) {
        LoanDocument loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // Ödeme gecikmesi varsa ceza faizi uygula
        LocalDateTime now = LocalDateTime.now();
        if (paymentDTO.getPaymentDate().isAfter(now)) {
            throw new RuntimeException("Payment date cannot be in the future");
        }

        long daysLate = now.toLocalDate().toEpochDay() - loan.getCreatedAt().toLocalDate().toEpochDay();
        double lateFee = 0;
        if (daysLate > 30) { // 30 günden fazla gecikme varsa ceza faizi uygula
            lateFee = paymentDTO.getPaymentAmount() * 0.02; // %2 gecikme faizi
        }

        double totalPayment = paymentDTO.getPaymentAmount() + lateFee;

        AccountDTO customerAccount = accountService.getAccountById(loan.getCustomerId());
        if (customerAccount.getBalance() < totalPayment) {
            throw new InsufficientBalanceException("Insufficient funds in account, including late fees");
        }

        double newBalance = customerAccount.getBalance() - totalPayment;
        accountService.updateAccountBalance(customerAccount.getAccountId(), newBalance);

        LoanPaymentDocument payment = new LoanPaymentDocument(null, loanId, totalPayment, paymentDTO.getPaymentDate());
        loanPaymentRepository.save(payment);
    }

    @Override
    public List<LoanPaymentDTO> getPaymentHistory(String loanId) {
        return loanPaymentRepository.findByLoanId(loanId).stream()
                .map(loanPaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LoanResponseDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loan -> new LoanResponseDTO(
                        loan.getId(),
                        loan.getCustomerId(),
                        loan.getAmount(),
                        loan.getTermInMonths(),
                        loan.getInterestRate(),
                        loan.getLoanType(),
                        loan.getStatus(),
                        loan.getApprovedBy(),
                        loan.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponseDTO updateLoan(String loanId, LoanResponseDTO updatedLoan) {
        System.out.println("Received loanId: " + loanId);
        LoanDocument existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with ID: " + loanId));

        existingLoan.setAmount(updatedLoan.getAmount());
        existingLoan.setTermInMonths(updatedLoan.getTermInMonths());
        existingLoan.setInterestRate(updatedLoan.getInterestRate());
        existingLoan.setLoanType(updatedLoan.getLoanType());
        existingLoan.setStatus(updatedLoan.getStatus());

        LoanDocument savedLoan = loanRepository.save(existingLoan);
        return loanMapper.toDTO(savedLoan);
    }

}
