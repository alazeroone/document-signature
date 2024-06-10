
package com.poc.project.documentsignature.mapper;

import com.poc.project.documentsignature.dto.SignerDTO;
import com.poc.project.documentsignature.entity.Signer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignerMapper {
    SignerDTO toDTO(Signer signer);
    Signer toEntity(SignerDTO signerDTO);
}
