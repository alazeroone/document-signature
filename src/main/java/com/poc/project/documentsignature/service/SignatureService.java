package com.poc.project.documentsignature.service;

import com.poc.project.documentsignature.entity.Document;
import com.poc.project.documentsignature.model.SignatureRequest;

public interface SignatureService {
    void sendDocumentForSignature(SignatureRequest request);
    String checkDocumentStatus(String documentId);
    Document retrieveSignedDocument(String documentId);

    void sendDocumentForSigning();

    void addDocumentToRequest();

    void removeDocumentFromRequest();

    void addSignerToRequest();

    void removeSignerFromRequest();
}

