package com.devbank.transfer.api.DTO;

import com.devbank.transfer.api.enums.TransferStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private String id; // Benzersiz transfer ID

    @NotNull
    private String senderAccountId; // Gönderici Hesap ID

    @NotNull
    private String receiverAccountId; // Alıcı Hesap ID

    @NotNull
    private Double amount; // Transfer edilen tutar

    private String description; // Transfer açıklaması

    @NotNull
    private TransferStatus status = TransferStatus.PENDING; // Transfer durumu

    private LocalDateTime transferTime; // Transferin zamanı
}
