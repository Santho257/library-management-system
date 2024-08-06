package com.santho.lms.dto.book;

import com.santho.lms.annotations.PublicationDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestDto {
    @NotBlank
    private String title;
    @NotNull
    private int authorId;
    @PublicationDate
    private LocalDate publicationDate;
    @NotBlank
    private String genre;
}
