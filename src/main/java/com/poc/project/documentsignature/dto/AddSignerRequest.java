
package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class AddSignerRequest {
    private Integer requestId;
    private SignerDTO signer;
}
