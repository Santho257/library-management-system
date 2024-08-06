package com.santho.lms.dto.borrowdetails;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowDetailsRequestDto {
    @NotNull
    private int bookId;
    private String borrowerId;
}
