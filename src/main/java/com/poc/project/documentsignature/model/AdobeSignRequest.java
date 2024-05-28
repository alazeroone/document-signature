package com.poc.project.documentsignature.model;

import lombok.Data;

@Data
public class AdobeSignRequest extends SignatureRequest {
    private String documentUrl;
    private String emailSubject;

    // Getters and setters
}
