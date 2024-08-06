package com.santho.lms.services;

import com.santho.lms.dto.auth.SignInDto;
import com.santho.lms.dto.auth.SignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    String login(SignInDto signInDto);

    String signUp(SignUpDto signUpDto);

    String adminSignUp(SignUpDto signUpDto);
}
