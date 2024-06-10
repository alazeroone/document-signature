
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.SignaturePosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignaturePositionRepository extends JpaRepository<SignaturePosition, Integer> {
}
