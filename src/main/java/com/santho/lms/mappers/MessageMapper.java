package com.santho.lms.mappers;

import com.santho.lms.dto.message.MessageType;
import com.santho.lms.dto.message.MessagesRequestDto;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.models.Messages;
import com.santho.lms.services.chat.ChatRoomService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageMapper {
    private final ChatRoomService temp;

    private static ChatRoomService chatRoomService;

    @PostConstruct
    public void init(){
        chatRoomService = temp;
    }
    public static Messages toMessage(MessagesRequestDto messagesRequestDto, String sender){
        return Messages.builder()
                .sender(sender)
                .receiver(messagesRequestDto.getReceiver())
                .content(messagesRequestDto.getContent())
                .chatId(chatRoomService.getRoomId(sender, messagesRequestDto.getReceiver(), true).get())
                .build();
    }

    public static MessagesResponseDto toMessageResponse(Messages messages){
        return MessagesResponseDto.builder()
                .sender(messages.getSender())
                .receiver(messages.getReceiver())
                .content(messages.getContent())
                .messageType(MessageType.CHAT)
                .sentAt(messages.getSentTime().toString())
                .build();
    }
}
