
package com.poc.project.documentsignature.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SignatureRequestDTO {
    private Integer requestId;
    private String createdBy;
    private String status;
    private Integer providerId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<DocumentDTO> documents;
    private List<SignerDTO> signers;
}
