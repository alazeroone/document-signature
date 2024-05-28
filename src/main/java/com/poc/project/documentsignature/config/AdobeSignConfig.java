package com.poc.project.documentsignature.config;


import com.poc.project.documentsignature.service.AdobeSignService;
import com.poc.project.documentsignature.service.SignatureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnSignatureService("adobeSignService")
public class AdobeSignConfig {
    @Bean(name = "adobeSignService")
    public SignatureService adobeSignService() {
        return new AdobeSignService();
    }
}
