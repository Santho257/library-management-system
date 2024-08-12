package com.santho.lms.services;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrowdetails.BorrowDetailsRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowDetailsService {
    ResponseEntity<Response<?>> getAll();

    ResponseEntity<Response<String>> borrow(BorrowDetailsRequestDto bdrDto);

    ResponseEntity<Response<String>> returnn(String username, int id);

    ResponseEntity<Response<?>> getByBorrower(String username);

    ResponseEntity<Response<?>> unBrReturned(String lowerCase);

    ResponseEntity<Response<?>> unReturned();

}
