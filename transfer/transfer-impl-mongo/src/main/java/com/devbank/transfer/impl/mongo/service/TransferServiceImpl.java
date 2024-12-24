package com.devbank.transfer.impl.mongo.service;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.api.service.TransferService;
import com.devbank.transfer.impl.mongo.document.TransferDocument;
import com.devbank.transfer.impl.mongo.repository.TransferRepository;
import com.devbank.transfer.impl.mongo.mapper.TransferMapper;
import com.devbank.error.management.exception.TransferFailedException;
import com.devbank.error.management.exception.TransferNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferMapper transferMapper;

    @Override
    public TransferDTO createTransfer(TransferDTO transferDTO) {
        transferDTO.setStatus(TransferStatus.PENDING);
        TransferDocument transferDocument = transferMapper.mapToDocument(transferDTO);
        transferDocument = transferRepository.save(transferDocument);
        return transferMapper.mapToDTO(transferDocument);
    }

    @Override
    public List<TransferDTO> getTransfersByAccount(Long accountId) {
        return transferRepository.findByFromAccountIdOrToAccountId(accountId, accountId)
                .stream()
                .map(transferMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransferDTO> getTransferHistory(Long customerId) {
        return transferRepository.findByCustomerId(customerId)
                .stream()
                .map(transferMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void completeTransfer(Long transferId) {
        TransferDocument transferDocument = transferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found"));
        if (transferDocument.getStatus() == TransferStatus.PENDING) {
            transferDocument.setStatus(TransferStatus.COMPLETED);
            transferRepository.save(transferDocument);
        }
    }

    @Override
    public void failTransfer(Long transferId) {
        TransferDocument transferDocument = transferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found"));
        if (transferDocument.getStatus() == TransferStatus.PENDING) {
            transferDocument.setStatus(TransferStatus.FAILED);
            transferRepository.save(transferDocument);
        } else {
            throw new TransferFailedException("Cannot fail a completed or already failed transfer");
        }
    }
}
