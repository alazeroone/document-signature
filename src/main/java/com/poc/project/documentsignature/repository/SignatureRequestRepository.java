
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.SignatureRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRequestRepository extends JpaRepository<SignatureRequest, Integer> {
}
