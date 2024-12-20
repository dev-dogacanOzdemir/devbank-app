package com.devbank.error.management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String errorName;
    private String message;
    private String details;
}
