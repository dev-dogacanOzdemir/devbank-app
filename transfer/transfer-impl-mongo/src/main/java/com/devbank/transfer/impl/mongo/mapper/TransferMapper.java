package com.devbank.transfer.impl.mongo.mapper;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {
    public TransferDTO mapToDTO(TransferDocument document) {
        return new TransferDTO(
                document.getTransferId(),
                document.getFromAccountId(),
                document.getToAccountId(),
                document.getAmount(),
                document.getTransferDate(),
                document.getCurrency(),
                document.getStatus()
        );
    }

    public TransferDocument mapToDocument(TransferDTO dto) {
        return new TransferDocument(
                dto.getTransferId(),
                dto.getFromAccountId(),
                dto.getToAccountId(),
                dto.getAmount(),
                dto.getTransferDate(),
                dto.getCurrency(),
                dto.getStatus()
        );
    }
}
