
package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class RemoveSignerRequest {
    private Integer requestId;
    private String signerId;
}
