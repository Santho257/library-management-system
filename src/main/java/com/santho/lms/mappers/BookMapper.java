package com.santho.lms.mappers;

import com.santho.lms.dto.author.AuthorResponseDto;
import com.santho.lms.dto.book.BookRequestDto;
import com.santho.lms.dto.book.BookResponseDto;
import com.santho.lms.models.Book;
import com.santho.lms.models.Genre;
import com.santho.lms.services.AuthorService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    @Autowired
    private AuthorService nonStatic;
    private static AuthorService authorService;

    @PostConstruct
    public void init(){
        authorService = nonStatic;
    }
    public static Book map(BookRequestDto bookDto){
        return Book.builder()
                .title(bookDto.getTitle())
                .genre(Genre.valueOf(bookDto.getGenre().toUpperCase()))
                .publicationDate(bookDto.getPublicationDate())
                .author(authorService.get(bookDto.getAuthorId()))
                .build();
    }

    public static BookResponseDto revMap(Book book){
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .genre(book.getGenre())
                .publicationDate(book.getPublicationDate().toString())
                .author(AuthorResponseDto.builder()
                        .name(book.getAuthor().getName())
                        .id(book.getAuthor().getId())
                        .build()
                )
                .build();
    }
}
