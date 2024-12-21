package com.devbank.transfer.impl.mongo.document;

import com.devbank.transfer.api.enums.TransferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transfers")
public class TransferDocument {

    @Id
    private Long transferId;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private Date transferDate;
    private String currency;
    private TransferStatus status;
}
