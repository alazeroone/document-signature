
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class SignatureRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    private String status;

    @Column(name = "provider_id")
    private Integer providerId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private Signer createdBySigner;

    @ManyToOne
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    private SignatureProvider signatureProvider;

    @OneToMany(mappedBy = "signatureRequest")
    private List<Document> documents;

    @OneToMany(mappedBy = "signatureRequest")
    private List<Signer> signers;
}
