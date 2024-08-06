package com.santho.lms.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequestDto {
    @NotBlank
    private String name;

    private void setName(String name){
        this.name = name.toUpperCase();
    }
}
