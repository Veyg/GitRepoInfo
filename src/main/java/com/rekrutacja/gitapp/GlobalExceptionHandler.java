package com.rekrutacja.gitapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("status", 404, "Message", ex.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<?> handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, HttpServletResponse response) {
        response.setContentType("application/json");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(Map.of("status", 406, "Message", "XML format is not supported"));
    }
}
