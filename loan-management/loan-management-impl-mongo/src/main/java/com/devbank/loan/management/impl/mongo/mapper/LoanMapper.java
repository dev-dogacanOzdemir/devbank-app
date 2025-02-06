package com.devbank.loan.management.impl.mongo.mapper;

import com.devbank.loan.management.api.DTO.LoanResponseDTO;
import com.devbank.loan.management.impl.mongo.document.LoanDocument;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponseDTO toDTO(LoanDocument document) {
        return new LoanResponseDTO(
                document.getId(),
                document.getCustomerId(),
                document.getAmount(),
                document.getTermInMonths(),
                document.getInterestRate(),
                document.getLoanType(),
                document.getStatus(),
                document.getApprovedBy(),
                document.getCreatedAt()
        );
    }

    public LoanDocument toDocument(LoanResponseDTO dto) {
        return new LoanDocument(
                dto.getLoanId(),
                dto.getCustomerId(),
                dto.getAmount(),
                dto.getTermInMonths(),
                dto.getInterestRate(),
                dto.getLoanType(),
                dto.getStatus(),
                dto.getApprovedBy(),
                dto.getCreatedAt(),
                null
        );
    }
}
