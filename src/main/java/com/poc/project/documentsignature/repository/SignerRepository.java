
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.Signer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignerRepository extends JpaRepository<Signer, String> {
}
