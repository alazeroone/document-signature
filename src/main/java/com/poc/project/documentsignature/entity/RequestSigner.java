
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class RequestSigner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_signer_id")
    private Integer requestSignerId;

    @Column(name = "request_id")
    private Integer requestId;

    @Column(name = "signer_id", length = 255)
    private String signerId;

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private SignatureRequest signatureRequest;

    @ManyToOne
    @JoinColumn(name = "signer_id", insertable = false, updatable = false)
    private Signer signer;
}
