package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.SignatureTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureTransactionRepository extends JpaRepository<SignatureTransaction, Long> {
    SignatureTransaction findByDocumentId(String documentId);
}
