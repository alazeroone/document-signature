package com.poc.project.documentsignature.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SignatureServiceCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // Placeholder logic: Check if a specific property or environment variable is set
        // For example, check if a property named "signature.service.enabled" is set to "true"
        String enabledProperty = context.getEnvironment().getProperty("signature.service.enabled");
        return "true".equalsIgnoreCase(enabledProperty);
    }
}

