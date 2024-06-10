
package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class SignerDTO {
    private String signerId;
    private Integer requestId;
    private String name;
    private String email;
    private String status;
}
