package com.poc.project.documentsignature.service;

import com.poc.project.documentsignature.entity.Document;
import com.poc.project.documentsignature.model.SignatureRequest;
import org.springframework.stereotype.Service;

@Service
public class DocuSignService implements SignatureService {

    @Override
    public void sendDocumentForSignature(SignatureRequest request) {
        // Implementation logic for sending document for signature via DocuSign
    }

    @Override
    public String checkDocumentStatus(String documentId) {
        // Implementation logic for checking document status via DocuSign
        return null;
    }

    @Override
    public Document retrieveSignedDocument(String documentId) {
        // Implementation logic for retrieving signed document via DocuSign
        return null;
    }
}

