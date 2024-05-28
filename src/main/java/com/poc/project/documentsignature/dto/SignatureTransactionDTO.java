package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class SignatureTransactionDTO {
    private Long id;
    private DocumentDTO document;
    private String status;
}
