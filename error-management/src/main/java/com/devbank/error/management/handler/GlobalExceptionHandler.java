package com.devbank.error.management.handler;

import com.devbank.error.management.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDetails> handleCustomException(CustomException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "CUSTOM_EXCEPTION",
                ex.getMessage(),
                "Custom exception occurred"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "VALIDATION_ERROR",
                "Validation failed for the request",
                validationErrors.toString()
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "GLOBAL_EXCEPTION",
                ex.getMessage(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "USER_NOT_FOUND",
                ex.getMessage(),
                "User not found in the system"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleAccountNotFoundException(AccountNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "ACCOUNT_NOT_FOUND",
                ex.getMessage(),
                "Account not found in the system"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "INSUFFICIENT_BALANCE",
                ex.getMessage(),
                "INSUFFICIENT_BALANCE"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(TransferFailedException.class)
    public ResponseEntity<ErrorDetails> handleTransferFailedException(TransferNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "TRANSFER_FAILED",
                ex.getMessage(),
                "Transfer failed."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTransferNotFoundException(TransferNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "TRANSFER_NOT_FOUND",
                ex.getMessage(),
                "Transfer not found."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
