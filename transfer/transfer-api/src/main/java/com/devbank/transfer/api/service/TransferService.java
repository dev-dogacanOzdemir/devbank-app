package com.devbank.transfer.api.service;

import com.devbank.transfer.api.DTO.TransferDTO;

import java.util.List;

public interface TransferService {

    TransferDTO createTransfer(TransferDTO transferDTO);

    // Hesabın transfer geçmişi
    List<TransferDTO> getTransfersByAccount(Long accountId);

    // Kullanıcının transfer geçmişi
    List<TransferDTO> getTransferHistory(Long customerId);

    void completeTransfer(Long transferId);

    void failTransfer(Long transferId);
}
