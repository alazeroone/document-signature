
package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.RequestSigner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestSignerRepository extends JpaRepository<RequestSigner, Integer> {
    Optional<RequestSigner> findByRequestIdAndSignerId(Integer requestId, String signerId);

    List<RequestSigner> findByRequestId(Integer requestId);

    void deleteByRequestIdAndSignerId(Integer requestId, String signerId);
}
