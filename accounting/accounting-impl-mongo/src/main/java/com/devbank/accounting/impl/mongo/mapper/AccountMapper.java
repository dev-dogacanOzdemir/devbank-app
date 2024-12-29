package com.devbank.accounting.impl.mongo.mapper;

import com.devbank.accounting.api.DTO.AccountDTO;
import com.devbank.accounting.impl.mongo.document.AccountDocument;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDocument toDocument(AccountDTO dto) {
        return new AccountDocument(
                dto.getAccountId(),
                dto.getCustomerId(),
                dto.getAccountType(),
                dto.getBalance(),
                dto.getUniqueAccountNumber(),
                dto.getCreatedAt(),
                dto.getInterestRate(),
                dto.getMaturityDate()
        );
    }

    public AccountDTO toDTO(AccountDocument document) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountId(document.getAccountId());
        dto.setCustomerId(document.getCustomerId());
        dto.setAccountType(document.getAccountType());
        dto.setBalance(document.getBalance());
        dto.setUniqueAccountNumber(document.getUniqueAccountNumber());
        dto.setCreatedAt(document.getCreatedAt());
        dto.setInterestRate(document.getInterestRate());
        dto.setMaturityDate(document.getMaturityDate());
        return dto;
    }
}
