package com.santho.lms.mappers;

import com.santho.lms.dto.auth.SignUpDto;
import com.santho.lms.dto.borrower.BorrowerRequestDto;
import com.santho.lms.models.Borrower;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.santho.lms.helper.ArrayToStringHelper.arrToString;
import static com.santho.lms.models.Role.BORROWER;

@RequiredArgsConstructor
@Service
public class BorrowMapper {
    private final PasswordEncoder temp;
    private static PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init(){
        passwordEncoder = temp;
    }
    public static Borrower map(BorrowerRequestDto brDto){
        return Borrower.builder()
                .username(brDto.getUsername())
                .name(brDto.getName())
                .role(BORROWER)
                .password(arrToString(brDto.getPassword()))
                .build();
    }

    public static Borrower toBorrower(SignUpDto signUpDto){
        return Borrower.builder()
                .username(signUpDto.getUsername())
                .name(signUpDto.getName())
                .role(BORROWER)
                .password(passwordEncoder.encode(arrToString(signUpDto.getPassword())))
                .build();
    }
}
