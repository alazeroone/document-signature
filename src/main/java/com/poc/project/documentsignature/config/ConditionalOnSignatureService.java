package com.poc.project.documentsignature.config;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Conditional(SignatureServiceCondition.class)
public @interface ConditionalOnSignatureService {
    String value();
}
