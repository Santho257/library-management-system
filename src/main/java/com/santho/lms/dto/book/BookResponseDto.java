package com.santho.lms.dto.book;

import com.santho.lms.dto.author.AuthorResponseDto;
import com.santho.lms.models.Genre;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BookResponseDto {
    private int id;
    private String title;
    private AuthorResponseDto author;
    private Genre genre;
    private String publicationDate;
}
