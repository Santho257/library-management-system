package com.santho.lms.conadvices;

import com.santho.lms.exception.UserExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionControllerAdvice {
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> handle(UserExistsException ex){
        String errorMessage = ex.getMessage();
        System.out.println(errorMessage);
        return ResponseEntity.status(400).body(errorMessage);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handle(UsernameNotFoundException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handle(BadCredentialsException ex){
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
