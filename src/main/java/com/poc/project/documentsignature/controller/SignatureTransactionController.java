package com.poc.project.documentsignature.controller;

import com.poc.project.documentsignature.dto.SignatureTransactionDTO;
import com.poc.project.documentsignature.model.SignatureRequest;
import com.poc.project.documentsignature.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signature-transactions")
public class SignatureTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<SignatureTransactionDTO> createSignatureTransaction(@RequestBody SignatureTransactionDTO signatureTransactionDTO) {
        SignatureTransactionDTO createdTransaction = transactionService.createSignatureTransaction(signatureTransactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Void> sendSignatureRequest(@PathVariable Long id, @RequestBody SignatureRequest signatureRequest) {
        transactionService.sendSignatureRequest(id, signatureRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignatureTransactionDTO> getSignatureTransactionById(@PathVariable Long id) {
        SignatureTransactionDTO transactionDTO = transactionService.getSignatureTransactionById(id);
        return ResponseEntity.ok(transactionDTO);
    }

    @GetMapping
    public ResponseEntity<List<SignatureTransactionDTO>> getAllSignatureTransactions() {
        List<SignatureTransactionDTO> transactions = transactionService.getAllSignatureTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SignatureTransactionDTO> updateSignatureTransaction(@PathVariable Long id, @RequestBody SignatureTransactionDTO signatureTransactionDTO) {
        SignatureTransactionDTO updatedTransaction = transactionService.updateSignatureTransaction(id, signatureTransactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignatureTransaction(@PathVariable Long id) {
        transactionService.deleteSignatureTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
