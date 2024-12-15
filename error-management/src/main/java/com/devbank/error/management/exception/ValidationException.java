package com.devbank.error.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends CustomException{

    public ValidationException(String message) {
        super(message);
    }

}
