
package com.poc.project.documentsignature.mapper;

import com.poc.project.documentsignature.dto.DocumentDTO;
import com.poc.project.documentsignature.entity.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDTO toDTO(Document document);
    Document toEntity(DocumentDTO documentDTO);
}
