package com.poc.project.documentsignature.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
public class SignatureTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Document document;

    @OneToMany(mappedBy = "transaction")
    private List<Recipient> recipients;

    private String status;
}
