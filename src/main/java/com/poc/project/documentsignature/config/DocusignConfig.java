package com.poc.project.documentsignature.config;


import com.poc.project.documentsignature.service.DocuSignService;
import com.poc.project.documentsignature.service.SignatureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnSignatureService("docusignService")
public class DocusignConfig {
    @Bean(name = "docusignService")
    public SignatureService docusignService() {
        return new DocuSignService();
    }
}