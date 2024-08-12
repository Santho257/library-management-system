package com.santho.lms.mappers;

import com.santho.lms.dto.botmessage.BotMessageRequestDto;
import com.santho.lms.dto.botmessage.BotMessageResponseDto;
import com.santho.lms.models.BotMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotMessagesMapper {
    public static BotMessages toBotMessage(BotMessageRequestDto request){
        return BotMessages.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
    }
    public static BotMessageResponseDto toBotMessageResponse(BotMessages messages){
        return BotMessageResponseDto.builder()
                .id(messages.getId())
                .question(messages.getQuestion())
                .answer(messages.getAnswer())
                .build();
    }
}
