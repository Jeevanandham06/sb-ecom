package com.ecommerce.sb_ecom.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}