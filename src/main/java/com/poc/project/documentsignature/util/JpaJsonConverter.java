package com.poc.project.documentsignature.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;

import jakarta.persistence.AttributeConverter;

//import javax.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter
public class JpaJsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }
}
