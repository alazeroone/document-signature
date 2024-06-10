
package com.poc.project.documentsignature.dto;

import lombok.Data;

@Data
public class AddDocumentRequest {
    private Integer requestId;
    private DocumentDTO document;
}
