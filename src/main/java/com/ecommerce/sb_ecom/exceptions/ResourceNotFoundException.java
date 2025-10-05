package com.ecommerce.sb_ecom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : %s", resourceName, field, fieldName));
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s : %d", resourceName, field, fieldId));
    }
}