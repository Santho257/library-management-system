package com.santho.lms.mappers;

import com.santho.lms.dto.borrowdetails.BorrowDetailsRequestDto;
import com.santho.lms.dto.borrowdetails.BorrowDetailsResponseDto;
import com.santho.lms.models.BorrowerDetails;
import com.santho.lms.services.BookService;
import com.santho.lms.services.BorrowerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowDetailsMapper {
    @Autowired
    private BookService tempBookService;
    @Autowired
    private BorrowerService tempBorrowerService;
    private static BookService bookService;
    private static BorrowerService borrowerService;

    @PostConstruct
    public void init(){
        bookService = tempBookService;
        borrowerService = tempBorrowerService;
    }
    public static BorrowerDetails map(BorrowDetailsRequestDto bdrDto){
        return BorrowerDetails.builder()
                .book(bookService.get(bdrDto.getBookId()))
                .borrower(borrowerService.get(bdrDto.getBorrowerId()))
                .borrowedOn(LocalDate.now())
                .build();
    }

    public static BorrowDetailsResponseDto revMap(BorrowerDetails bd){
        return BorrowDetailsResponseDto.builder()
                .id(bd.getId())
                .title(bd.getBook().getTitle())
                .borrowerId(bd.getBorrower().getUsername())
                .borrowedOn(bd.getBorrowedOn().toString())
                .returnedOn(bd.getReturnedOn() != null ? bd.getReturnedOn().toString() : null)
                .build();
    }
}
