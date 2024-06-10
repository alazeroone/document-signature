
package com.poc.project.documentsignature.mapper;

import com.poc.project.documentsignature.dto.CreateSignatureRequest;
import com.poc.project.documentsignature.entity.SignatureRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class, SignerMapper.class})
public interface CreateSignatureRequestMapper {
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "providerId", target = "providerId")
    SignatureRequest toEntity(CreateSignatureRequest createSignatureRequest);
}
