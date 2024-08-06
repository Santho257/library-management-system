package com.santho.lms.services;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.dto.auth.SignInDto;
import com.santho.lms.dto.auth.SignUpDto;
import static com.santho.lms.helper.ArrayToStringHelper.arrToString;

import com.santho.lms.exception.UserExistsException;
import com.santho.lms.mappers.BorrowMapper;
import com.santho.lms.models.Borrower;
import com.santho.lms.models.Role;
import com.santho.lms.services.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BorrowerDao borrowerDao;
    @Override
    public String login(SignInDto signInDto){
        Borrower borrower = borrowerDao.findById(signInDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists:: User Email : " + signInDto.getUsername()));
        if(!passwordEncoder.matches(arrToString(signInDto.getPassword()), borrower.getPassword())){
            throw new BadCredentialsException("Username Password Doesn't match");
        }
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDto.getUsername(),arrToString(signInDto.getPassword()))
            );
        }
        catch (AuthenticationException ex){
            throw new BadCredentialsException("Username Password Doesn't match");
        }
        return jwtService.generateToken(User
                .withUsername(borrower.getUsername())
                .password(borrower.getPassword())
                .roles(borrower.getRole().toString())
                .build());
    }

    @Override
    public String signUp(SignUpDto signUpDto) {
        if(borrowerDao.existsById(signUpDto.getUsername()))
            throw new UserExistsException("User already Exists:: User Email : "+signUpDto.getUsername());
        Borrower borrower = borrowerDao.save(BorrowMapper.toBorrower(signUpDto));
        return jwtService.generateToken( User
                .withUsername(borrower.getUsername())
                .password(borrower.getPassword())
                .roles(borrower.getRole().toString())
                .build());
    }

    @Override
    public String adminSignUp(SignUpDto signUpDto) {
        if(borrowerDao.existsById(signUpDto.getUsername()))
            throw new UserExistsException("User already Exists:: User Email : "+signUpDto.getUsername());
        Borrower borrower = borrowerDao.save(BorrowMapper.toBorrower(signUpDto));
        borrower.setRole(Role.ADMIN);
        return jwtService.generateToken( User
                .withUsername(borrower.getUsername())
                .password(borrower.getPassword())
                .roles(borrower.getRole().toString())
                .build());
    }
}
