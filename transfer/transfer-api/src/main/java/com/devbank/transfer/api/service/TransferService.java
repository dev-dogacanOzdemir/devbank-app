package com.devbank.transfer.api.service;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.enums.TransferStatus;

import java.util.List;

public interface TransferService {

    TransferDTO createTransfer(TransferDTO transferDTO); // Yeni transfer oluşturma

    TransferDTO createScheduledTransfer(TransferDTO transferDTO); // İleri tarihli transfer oluşturma

    List<TransferDTO> getTransfersByAccountId(String accountId); // Hesap ID'ye göre tüm transferleri listeleme

    TransferDTO updateTransferStatus(String transferId, TransferStatus status); // Transfer durumunu güncelleme
}

