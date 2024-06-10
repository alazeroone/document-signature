
package com.poc.project.documentsignature.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class SignatureProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private Integer providerId;

    private String name;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "base_url")
    private String baseUrl;

    // Add other relevant fields here
}
