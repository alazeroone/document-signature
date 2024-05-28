package com.poc.project.documentsignature.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentDTO {
    private Long id;
    private String title;
    private String content;
    private List<RecipientDTO> recipients;
}
