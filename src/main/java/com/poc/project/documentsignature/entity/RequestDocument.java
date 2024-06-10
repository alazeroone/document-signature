
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class RequestDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_document_id")
    private Integer requestDocumentId;

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "document_id", length = 255)
    private String documentId;

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private SignatureRequest signatureRequest;

    @ManyToOne
    @JoinColumn(name = "document_id", insertable = false, updatable = false)
    private Document document;
}
