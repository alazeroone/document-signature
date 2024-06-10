
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "signature_id")
    private Integer signatureId;

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "signer_id", length = 255)
    private String signerId;

    private String status;

    @Column(name = "signed_at")
    private Timestamp signedAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private SignatureRequest signatureRequest;

    @ManyToOne
    @JoinColumn(name = "signer_id", insertable = false, updatable = false)
    private Signer signer;
}
