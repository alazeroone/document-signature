package com.poc.project.documentsignature.util;

import com.poc.project.documentsignature.dto.DocumentDTO;
import com.poc.project.documentsignature.dto.RecipientDTO;
import com.poc.project.documentsignature.dto.SignatureTransactionDTO;
import com.poc.project.documentsignature.entity.Document;
import com.poc.project.documentsignature.entity.Recipient;
import com.poc.project.documentsignature.entity.SignatureTransaction;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class TransactionConverter {

    public static DocumentDTO convertToDto(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        BeanUtils.copyProperties(document, documentDTO);
        documentDTO.setRecipients(document.getRecipients().stream()
                .map(TransactionConverter::convertToDto)
                .collect(Collectors.toList()));
        return documentDTO;
    }

    public static Document convertToEntity(DocumentDTO documentDTO) {
        Document document = new Document();
        BeanUtils.copyProperties(documentDTO, document);
        document.setRecipients(documentDTO.getRecipients().stream()
                .map(TransactionConverter::convertToEntity)
                .collect(Collectors.toList()));
        return document;
    }

    public static RecipientDTO convertToDto(Recipient recipient) {
        RecipientDTO recipientDTO = new RecipientDTO();
        BeanUtils.copyProperties(recipient, recipientDTO);
        return recipientDTO;
    }

    public static Recipient convertToEntity(RecipientDTO recipientDTO) {
        Recipient recipient = new Recipient();
        BeanUtils.copyProperties(recipientDTO, recipient);
        return recipient;
    }

    public static SignatureTransactionDTO convertToDto(SignatureTransaction transaction) {
        SignatureTransactionDTO transactionDTO = new SignatureTransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);
        transactionDTO.setDocument(convertToDto(transaction.getDocument()));
        return transactionDTO;
    }

    public static SignatureTransaction convertToEntity(SignatureTransactionDTO transactionDTO) {
        SignatureTransaction transaction = new SignatureTransaction();
        BeanUtils.copyProperties(transactionDTO, transaction);
        transaction.setDocument(convertToEntity(transactionDTO.getDocument()));
        return transaction;
    }
}
