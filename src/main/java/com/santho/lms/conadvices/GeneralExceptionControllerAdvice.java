package com.santho.lms.conadvices;

import com.santho.lms.exception.AlreadReturnedException;
import com.santho.lms.exception.UserExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(err -> {
                    errors.put(((FieldError)err).getField(), err.getDefaultMessage());
                });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handle(UnsupportedOperationException ex){
        return ResponseEntity.status(403).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(AlreadReturnedException.class)
    public ResponseEntity<String> handle(AlreadReturnedException     ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }


}
