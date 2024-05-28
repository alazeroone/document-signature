package com.poc.project.documentsignature.repository;

import com.poc.project.documentsignature.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
