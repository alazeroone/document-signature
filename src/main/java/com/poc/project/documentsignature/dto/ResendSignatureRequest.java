
package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class ResendSignatureRequest {
    private Integer requestId;
    private String signerId;
}
