package com.santho.lms.controllers;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrowdetails.BorrowDetailsRequestDto;
import com.santho.lms.services.BorrowDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @GetMapping("/borrower/{borrower}")
    public ResponseEntity<Response<?>> getByBorrower(@PathVariable String borrower){
        return borrowDetailsService.getByBorrower(borrower.toLowerCase());
    }
    @GetMapping("/borrower/{borrower}/unreturned")
    public ResponseEntity<Response<?>> unBrReturned(@PathVariable String borrower){
        return borrowDetailsService.unBrReturned(borrower.toLowerCase());
    }
    @PostMapping("/borrow")
    public ResponseEntity<Response<String>> borrow(@Valid @RequestBody BorrowDetailsRequestDto bdrDto, Principal principal){
        bdrDto.setBorrowerId(principal.getName());
        return borrowDetailsService.borrow(bdrDto);
    }
    @PostMapping("/return/{bookId}")
    public ResponseEntity<Response<String>> returnn(Principal principal, @PathVariable int bookId){
        return borrowDetailsService.returnn(principal.getName(), bookId);
    }
}
