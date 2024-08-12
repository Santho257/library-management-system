package com.santho.lms.controllers;

import com.santho.lms.dto.auth.AuthResponseDto;
import com.santho.lms.dto.auth.SignInDto;
import com.santho.lms.dto.auth.SignUpDto;
import com.santho.lms.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody SignInDto signInDto){
        return ResponseEntity.ok(authenticationService.login(signInDto));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(authenticationService.signUp(signUpDto));
    }

    @PostMapping("/admin-signup")
    public ResponseEntity<AuthResponseDto> admin(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(authenticationService.adminSignUp(signUpDto));
    }
}
