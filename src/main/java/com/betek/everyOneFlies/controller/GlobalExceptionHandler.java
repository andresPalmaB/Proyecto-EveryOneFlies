package com.betek.everyOneFlies.controller;

import com.betek.everyOneFlies.exception.ErrorDetails;
import com.betek.everyOneFlies.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String stackTrace = ex.getStackTrace()[0].toString();
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage(), stackTrace), HttpStatus.NOT_FOUND);
    }

}
