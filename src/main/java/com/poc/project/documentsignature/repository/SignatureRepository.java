
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signature, Integer> {
}
