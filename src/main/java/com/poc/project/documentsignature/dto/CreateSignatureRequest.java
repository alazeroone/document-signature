
package com.poc.project.documentsignature.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateSignatureRequest {
    private String createdBy;
    private Integer providerId;
    private List<DocumentDTO> documents;
    private List<SignerDTO> signers;
}
