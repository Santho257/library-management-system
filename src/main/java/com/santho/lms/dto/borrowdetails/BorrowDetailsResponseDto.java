package com.santho.lms.dto.borrowdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowDetailsResponseDto {
    private int id;
    private String title;
    private String borrowerId;
    private String borrowedOn;
    private String returnedOn;
}
