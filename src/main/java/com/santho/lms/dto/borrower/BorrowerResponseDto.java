package com.santho.lms.dto.borrower;

import com.santho.lms.models.Role;
import com.santho.lms.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerResponseDto {
    private String username;
    private String name;
    private Status status;
    private Role role;
}
