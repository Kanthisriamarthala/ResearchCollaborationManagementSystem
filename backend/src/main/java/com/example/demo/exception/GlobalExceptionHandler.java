package com.example.demo.exception;

import com.example.demo.common.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiErrorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicate(
            DuplicateResourceException ex) {

        return new ResponseEntity<>(
                new ApiErrorResponse(ex.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(
            BadRequestException ex) {

        return new ResponseEntity<>(
                new ApiErrorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex) {

        return new ResponseEntity<>(
                new ApiErrorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}