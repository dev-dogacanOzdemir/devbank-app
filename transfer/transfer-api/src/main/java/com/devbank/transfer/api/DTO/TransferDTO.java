package com.devbank.transfer.api.DTO;

import com.devbank.transfer.api.enums.TransferStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private Long transferId;

    @NotNull(message = "From Account ID cannot be null")
    private Long fromAccountId;

    @NotNull(message = "To Account ID cannot be null")
    private Long toAccountId;

    @NotNull(message = "Amount cannot be null")
    private Double amount;

    private Date transferDate;

    @NotNull(message = "Currency cannot be null")
    private String currency;

    private TransferStatus status;
}
