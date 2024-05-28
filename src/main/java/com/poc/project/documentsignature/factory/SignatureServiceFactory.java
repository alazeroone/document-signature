package com.poc.project.documentsignature.factory;


import com.poc.project.documentsignature.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SignatureServiceFactory {
    @Autowired
    private ApplicationContext context;

    public SignatureService getSignatureService(String serviceType) {
        return context.getBean(serviceType, SignatureService.class);
    }
}

