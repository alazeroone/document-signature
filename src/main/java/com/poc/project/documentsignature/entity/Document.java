package com.poc.project.documentsignature.entity;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
}
