package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
