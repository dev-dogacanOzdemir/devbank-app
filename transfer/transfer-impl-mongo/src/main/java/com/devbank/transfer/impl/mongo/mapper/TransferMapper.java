package com.devbank.transfer.impl.mongo.mapper;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransferMapper {
    public TransferDTO toDTO(TransferDocument document) {
        return new TransferDTO(
                document.getId(),
                document.getSenderAccountId(),
                document.getReceiverAccountId(),
                document.getAmount(),
                document.getDescription(),
                document.getStatus(),
                document.getTransferTime() != null ? document.getTransferTime() : LocalDateTime.now()
        );
    }

    public TransferDocument toDocument(TransferDTO dto) {
        return new TransferDocument(
                dto.getId(),
                dto.getSenderAccountId(),
                dto.getReceiverAccountId(),
                dto.getAmount(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getTransferTime() != null ? dto.getTransferTime() : LocalDateTime.now() // Default değer
        );
    }
}
