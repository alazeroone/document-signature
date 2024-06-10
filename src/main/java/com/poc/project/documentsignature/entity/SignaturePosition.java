
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class SignaturePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Integer positionId;

    @Column(name = "document_id", length = 255)
    private String documentId;

    @Column(name = "signer_id", length = 255)
    private String signerId;

    private Integer page;
    private Integer xCoordinate;
    private Integer yCoordinate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "document_id", insertable = false, updatable = false)
    private Document document;

    @ManyToOne
    @JoinColumn(name = "signer_id", insertable = false, updatable = false)
    private Signer signer;
}
