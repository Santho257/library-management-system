package com.santho.lms.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class SignUpDto {
    @NotBlank(message = "username is required")
    @Email(message = "Email Should be Valid")
    private String username;
    @NotBlank(message = "name is required")
    private String name;
    @NotEmpty(message = "password is required")
    @Size(min = 8, max = 20,message = "password length should be min 8 and max 20 characters")
    private char[] password;
}
