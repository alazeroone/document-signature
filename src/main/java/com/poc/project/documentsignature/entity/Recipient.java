package com.poc.project.documentsignature.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "id")
    private SignatureTransaction signatureTransaction;

    // Getters and setters
}
