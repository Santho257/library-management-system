package com.santho.lms.dto.message;

import com.santho.lms.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiverResponseDto {
    private String email;
    private String name;
    private Status status;
    private MessageType messageType;
}
