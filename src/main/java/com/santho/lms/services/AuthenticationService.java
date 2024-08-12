package com.santho.lms.services;

import com.santho.lms.dto.auth.AuthResponseDto;
import com.santho.lms.dto.auth.SignInDto;
import com.santho.lms.dto.auth.SignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthResponseDto login(SignInDto signInDto);

    AuthResponseDto signUp(SignUpDto signUpDto);

    AuthResponseDto adminSignUp(SignUpDto signUpDto);
}
