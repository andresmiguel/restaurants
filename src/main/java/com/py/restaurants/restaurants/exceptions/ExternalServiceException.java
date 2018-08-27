package com.py.restaurants.restaurants.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ExternalServiceException extends Exception {
    public ExternalServiceException(String message) {
        super(message);
    }
}
