package com.betek.ms_pay.controller;

import com.betek.ms_pay.exceptions.ErrorMassage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ErrorMassage.class)
    public ResponseEntity<String> handleNotFoundException(ErrorMassage ex, WebRequest request) {
        String stackTrace = ex.getMessage();
        return new ResponseEntity<>(stackTrace, HttpStatus.NOT_FOUND);
}

}