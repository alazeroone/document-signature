package com.poc.project.documentsignature.service;

import com.poc.project.documentsignature.dto.SignatureTransactionDTO;
import com.poc.project.documentsignature.entity.SignatureTransaction;
import com.poc.project.documentsignature.factory.SignatureServiceFactory;
import com.poc.project.documentsignature.model.SignatureRequest;
import com.poc.project.documentsignature.repository.SignatureTransactionRepository;
import com.poc.project.documentsignature.util.TransactionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private SignatureTransactionRepository signatureTransactionRepository;

    @Autowired
    private SignatureServiceFactory signatureServiceFactory;

    public SignatureTransactionDTO createSignatureTransaction(SignatureTransactionDTO signatureTransactionDTO) {
        SignatureTransaction transaction = TransactionConverter.convertToEntity(signatureTransactionDTO);
        SignatureTransaction savedTransaction = signatureTransactionRepository.save(transaction);
        return TransactionConverter.convertToDto(savedTransaction);
    }

    public void sendSignatureRequest(Long id, SignatureRequest signatureRequest) {
        SignatureTransaction transaction = signatureTransactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        SignatureService signatureService = signatureServiceFactory.getSignatureService(transaction.getServiceType());
        signatureService.sendSignatureRequest(transaction, signatureRequest);
    }

    public SignatureTransactionDTO getSignatureTransactionById(Long id) {
        SignatureTransaction transaction = signatureTransactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        return TransactionConverter.convertToDto(transaction);
    }

    public List<SignatureTransactionDTO> getAllSignatureTransactions() {
        return signatureTransactionRepository.findAll().stream()
                .map(TransactionConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public SignatureTransactionDTO updateSignatureTransaction(Long id, SignatureTransactionDTO signatureTransactionDTO) {
        SignatureTransaction transaction = signatureTransactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transaction.setServiceType(signatureTransactionDTO.getServiceType());
        transaction.setDocument(TransactionConverter.convertToEntity(signatureTransactionDTO.getDocument()));
        SignatureTransaction updatedTransaction = signatureTransactionRepository.save(transaction);
        return TransactionConverter.convertToDto(updatedTransaction);
    }

    public void deleteSignatureTransaction(Long id) {
        signatureTransactionRepository.deleteById(id);
    }
}
