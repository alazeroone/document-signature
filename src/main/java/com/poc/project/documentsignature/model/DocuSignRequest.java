package com.poc.project.documentsignature.model;

import lombok.Data;

@Data
public class DocuSignRequest extends SignatureRequest {
    private String documentContent;
    private String callbackUrl;

    // Getters and setters
}
