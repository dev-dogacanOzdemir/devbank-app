package com.devbank.transfer.rest.controller;

import com.devbank.transfer.api.DTO.TransferDTO;
import com.devbank.transfer.api.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferDTO> createTransfer(@Valid @RequestBody TransferDTO transferDTO) {
        return ResponseEntity.ok(transferService.createTransfer(transferDTO));
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<TransferDTO>> getTransfersByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(transferService.getTransfersByAccount(accountId));
    }

    @PatchMapping("/{transferId}/complete")
    public ResponseEntity<Void> completeTransfer(@PathVariable Long transferId) {
        transferService.completeTransfer(transferId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{transferId}/fail")
    public ResponseEntity<Void> failTransfer(@PathVariable Long transferId) {
        transferService.failTransfer(transferId);
        return ResponseEntity.ok().build();
    }
}
