
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Signer {
    @Id
    @Column(name = "signer_id", length = 255)
    private String signerId;

    @Column(name = "request_id")
    private Integer requestId;

    private String name;
    private String email;
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private SignatureRequest signatureRequest;
}
