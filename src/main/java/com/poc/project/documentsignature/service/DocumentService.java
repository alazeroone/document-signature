package com.poc.project.documentsignature.service;

import com.poc.project.documentsignature.dto.DocumentDTO;
import com.poc.project.documentsignature.entity.Document;
import com.poc.project.documentsignature.repository.DocumentRepository;
import com.poc.project.documentsignature.util.TransactionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = TransactionConverter.convertToEntity(documentDTO);
        Document savedDocument = documentRepository.save(document);
        return TransactionConverter.convertToDto(savedDocument);
    }

    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
        return TransactionConverter.convertToDto(document);
    }

    public List<DocumentDTO> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(TransactionConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public DocumentDTO updateDocument(Long id, DocumentDTO documentDTO) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Document not found"));
        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        Document updatedDocument = documentRepository.save(document);
        return TransactionConverter.convertToDto(updatedDocument);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
