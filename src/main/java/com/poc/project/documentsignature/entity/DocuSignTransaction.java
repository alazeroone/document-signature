
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class DocuSignTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "request_id")
    private Integer requestId;

    private String status;

    @Column(name = "external_id", length = 255)
    private String externalId;

    // Add other DocuSign-specific fields

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private SignatureRequest signatureRequest;
}
