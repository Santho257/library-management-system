package com.santho.lms.controllers;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.book.BookResponseDto;
import com.santho.lms.dto.borrowdetails.BorrowDetailsRequestDto;
import com.santho.lms.dto.borrowdetails.BorrowDetailsResponseDto;
import com.santho.lms.services.BorrowDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/library")
public class BorrowDetailsController {
    @Autowired
    public BorrowDetailsService borrowDetailsService;

    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        return borrowDetailsService.getAll();
    }

    @GetMapping("/unreturned")
    public ResponseEntity<Response<?>> unReturned(){
        return borrowDetailsService.unReturned();
    }
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BorrowDetailsResponseDto>> trackByBooks(@PathVariable int bookId){
        return ResponseEntity.ok(borrowDetailsService.trackByBookId(bookId));
    }
    @GetMapping("/borrower/{borrower}")
    public ResponseEntity<Response<List<BorrowDetailsResponseDto>>> getByBorrower(@PathVariable String borrower){
        return ResponseEntity.ok(new Response<List<BorrowDetailsResponseDto>>(borrowDetailsService.getByBorrower(borrower.toLowerCase()), HttpStatus.ACCEPTED));
    }
    @GetMapping("/borrower/{borrower}/unreturned")
    public ResponseEntity<Response<?>> unBrReturned(@PathVariable String borrower){
        return borrowDetailsService.unBrReturned(borrower.toLowerCase());
    }
    @GetMapping("/borrower")
    public ResponseEntity<Response<List<BorrowDetailsResponseDto>>> getBorrowed(Principal principal){
        return ResponseEntity.ok(new Response<List<BorrowDetailsResponseDto>>(borrowDetailsService.getByBorrower(principal.getName().toLowerCase()), HttpStatus.ACCEPTED));
    }
    @GetMapping("/borrower/unreturned")
    public ResponseEntity<Response<?>> getBrUnreturned(Principal principal){
        return borrowDetailsService.unBrReturned(principal.getName().toLowerCase());
    }
    @PostMapping("/borrow")
    public ResponseEntity<Response<String>> borrow(@Valid @RequestBody BorrowDetailsRequestDto bdrDto, Principal principal){
        bdrDto.setBorrowerId(principal.getName());
        return borrowDetailsService.borrow(bdrDto);
    }
    @PostMapping("/return/{id}")
    public ResponseEntity<Response<String>> returnn(Principal principal, @PathVariable int id){
        return borrowDetailsService.returnn(principal.getName(), id);
    }
}
