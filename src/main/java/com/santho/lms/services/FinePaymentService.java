package com.santho.lms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinePaymentService {
    private final BorrowDetailsService borrowDetailsService;

    public Integer fineAmount(String username){
//        Response<?> body = borrowDetailsService.getByBorrower(username).getBody();

        return 0;
    }
}
