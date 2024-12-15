package com.devbank.error.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends CustomException{

    public UserNotFoundException(String message) {
        super(message);
    }

}
