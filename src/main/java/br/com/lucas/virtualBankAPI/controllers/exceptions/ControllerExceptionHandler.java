package br.com.lucas.virtualBankAPI.controllers.exceptions;

import br.com.lucas.virtualBankAPI.services.exceptions.*;
import br.com.lucas.virtualBankAPI.services.exceptions.IllegalArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError(ex.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DivergentDataException.class)
    public ResponseEntity<StandardError> divergentData(DivergentDataException ex, HttpServletRequest request) {
        StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<StandardError> insufficientBalance(InsufficientBalanceException ex, HttpServletRequest request) {
        StandardError error = new StandardError(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
