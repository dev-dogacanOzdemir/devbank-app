package com.devbank.transfer.rest.controller;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.enums.TransferStatus;
import com.devbank.transfer.api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferDTO> createTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        TransferDTO createdTransfer = transferService.createTransfer(transferDTO);
        return ResponseEntity.ok(createdTransfer);
    }

    @PostMapping("/scheduled")
    public ResponseEntity<TransferDTO> createScheduledTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        TransferDTO createdTransfer = transferService.createScheduledTransfer(transferDTO);
        return ResponseEntity.ok(createdTransfer);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransferDTO>> getTransfersByAccountId(@PathVariable String accountId) {
        List<TransferDTO> transfers = transferService.getTransfersByAccountId(accountId);
        return ResponseEntity.ok(transfers);
    }

    @PutMapping("/{transferId}/status")
    public ResponseEntity<TransferDTO> updateTransferStatus(
            @PathVariable String transferId,
            @RequestParam TransferStatus status) {
        TransferDTO updatedTransfer = transferService.updateTransferStatus(transferId, status);
        return ResponseEntity.ok(updatedTransfer);
    }
}
