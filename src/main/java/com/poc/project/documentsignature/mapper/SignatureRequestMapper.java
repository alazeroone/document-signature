
package com.poc.project.documentsignature.mapper;

import com.poc.project.documentsignature.dto.SignatureRequestDTO;
import com.poc.project.documentsignature.entity.SignatureRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignatureRequestMapper {
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "providerId", target = "providerId")
    SignatureRequestDTO toDTO(SignatureRequest signatureRequest);
    SignatureRequest toEntity(SignatureRequestDTO signatureRequestDTO);
}
