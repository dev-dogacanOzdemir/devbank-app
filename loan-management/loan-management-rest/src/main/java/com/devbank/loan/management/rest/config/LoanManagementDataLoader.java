package com.devbank.loan.management.rest.config;

import com.devbank.loan.management.api.enums.LoanStatus;
import com.devbank.loan.management.api.enums.LoanType;
import com.devbank.loan.management.impl.mongo.document.LoanDocument;
import com.devbank.loan.management.impl.mongo.document.LoanPaymentDocument;
import com.devbank.loan.management.impl.mongo.repository.LoanPaymentRepository;
import com.devbank.loan.management.impl.mongo.repository.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration

public class LoanManagementDataLoader {

    private final LoanRepository loanRepository;
    private final LoanPaymentRepository loanPaymentRepository;

    public LoanManagementDataLoader(LoanRepository loanRepository, LoanPaymentRepository loanPaymentRepository) {
        this.loanRepository = loanRepository;
        this.loanPaymentRepository = loanPaymentRepository;
    }

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (loanRepository.count() == 0) {
                LoanDocument loan1 = new LoanDocument(null, "12345", 10000.0, 12, 5.5, LoanType.PERSONAL, LoanStatus.PENDING, null, LocalDateTime.now(), null);
                LoanDocument loan2 = new LoanDocument(null, "67890", 50000.0, 24, 4.8, LoanType.MORTGAGE, LoanStatus.APPROVED, "admin", LocalDateTime.now(), null);
                LoanDocument loan3 = new LoanDocument(null, "11223", 20000.0, 36, 6.2, LoanType.BUSINESS, LoanStatus.REJECTED, null, LocalDateTime.now(), null);

                loanRepository.saveAll(List.of(loan1, loan2, loan3));
                System.out.println("Sample loan data loaded into database.");
            }

            if (loanPaymentRepository.count() == 0) {
                LoanPaymentDocument payment1 = new LoanPaymentDocument(null, "67890", 2000.0, LocalDateTime.now().minusMonths(1));
                LoanPaymentDocument payment2 = new LoanPaymentDocument(null, "67890", 2000.0, LocalDateTime.now().minusMonths(2));
                LoanPaymentDocument payment3 = new LoanPaymentDocument(null, "11223", 500.0, LocalDateTime.now().minusDays(15));

                loanPaymentRepository.saveAll(List.of(payment1, payment2, payment3));
                System.out.println("Sample loan payment data loaded into database.");
            }
        };
    }
}
