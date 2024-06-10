
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.RequestDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestDocumentRepository extends JpaRepository<RequestDocument, Integer> {
    List<RequestDocument> findByRequestId(Integer requestId);

    void deleteByRequestIdAndDocumentId(Integer requestId, String documentId);
}
