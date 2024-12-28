package com.devbank.transfer.impl.mongo.document;

import com.devbank.transfer.api.enums.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transfers")
public class TransferDocument {

    @Id
    private String id; // Transfer ID

    private String senderAccountId; // Gönderici Hesap ID

    private String receiverAccountId; // Alıcı Hesap ID

    private Double amount; // Transfer edilen tutar

    private String description; // Transfer açıklaması

    private TransferStatus status; // Transfer durumu

    private LocalDateTime transferTime; // Transfer zamanı
}
