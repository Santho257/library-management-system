package com.santho.lms.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessagesResponseDto {
    private MessageType messageType;
    private String sender;
    private String receiver;
    private String content;
    private String sentAt;
}
