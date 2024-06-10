
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.SignatureProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureProviderRepository extends JpaRepository<SignatureProvider, Integer> {
}
