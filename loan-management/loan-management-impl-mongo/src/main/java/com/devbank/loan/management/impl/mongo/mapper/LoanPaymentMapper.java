package com.devbank.loan.management.impl.mongo.mapper;

import com.devbank.loan.management.api.DTO.LoanPaymentDTO;
import com.devbank.loan.management.impl.mongo.document.LoanPaymentDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanPaymentMapper {

    public LoanPaymentDTO toDTO(LoanPaymentDocument document) {
        return new LoanPaymentDTO(
                document.getPaymentAmount(),
                document.getPaymentDate()
        );
    }

    public LoanPaymentDocument toDocument(LoanPaymentDTO dto) {
        return new LoanPaymentDocument(
                null,
                null,
                dto.getPaymentAmount(),
                dto.getPaymentDate()
        );
    }

    public List<LoanPaymentDTO> toDTOList(List<LoanPaymentDocument> documents) {
        return documents.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
