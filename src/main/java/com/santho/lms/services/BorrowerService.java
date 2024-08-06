package com.santho.lms.services;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrower.BorrowerRequestDto;
import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.models.Borrower;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowerService {
    ResponseEntity<Response<String>> add(BorrowerRequestDto borrower);

    ResponseEntity<Response<List<BorrowerResponseDto>>> getAll();

    ResponseEntity<Response<?>> sort();

    ResponseEntity<Response<?>> search(String key);

    ResponseEntity<Response<String>> delete(String id);

    Borrower get(String username);
}
