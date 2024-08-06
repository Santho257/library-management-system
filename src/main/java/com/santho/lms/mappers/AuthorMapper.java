package com.santho.lms.mappers;

import com.santho.lms.dto.author.AuthorRequestDto;
import com.santho.lms.dto.author.AuthorResponseCountDto;
import com.santho.lms.dto.author.AuthorResponseDto;
import com.santho.lms.dto.borrower.BorrowerRequestDto;
import com.santho.lms.helper.ArrayToStringHelper;
import com.santho.lms.models.Author;
import com.santho.lms.models.Borrower;

public class AuthorMapper {
    public static Author map(AuthorRequestDto authDto){
        return Author.builder()
                .name(authDto.getName())
                .build();
    }
    public static AuthorResponseDto revMap(Author au){
        return AuthorResponseDto.builder()
                .id(au.getId())
                .name(au.getName())
                .build();
    }

    public static AuthorResponseCountDto revCountMap(Author au){
        return AuthorResponseCountDto.builder()
                .id(au.getId())
                .name(au.getName())
                .count(au.getWrittenBooks().size())
                .build();
    }
}
