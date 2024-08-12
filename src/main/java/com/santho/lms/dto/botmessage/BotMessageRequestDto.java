package com.santho.lms.dto.botmessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BotMessageRequestDto {
    private String question;
    private String answer;
}
