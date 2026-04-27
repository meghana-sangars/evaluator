package com.expression.evaluator.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            NumberFormatException.class
    })
    public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmetic(ArithmeticException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonError(Exception ex) {
        return ResponseEntity.badRequest().body("Malformed JSON request");
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(Exception ex) {
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(500).body("Internal server error");
    }
}
