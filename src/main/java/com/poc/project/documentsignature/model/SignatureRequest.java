package com.poc.project.documentsignature.model;

import com.poc.project.documentsignature.dto.RecipientDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class SignatureRequest {
    private String serviceType;
    private List<RecipientDTO> recipients;

    // Getters and setters
}