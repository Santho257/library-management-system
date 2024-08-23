package com.santho.lms.controllers;

import com.santho.lms.dto.message.MessageType;
import com.santho.lms.dto.message.MessagesRequestDto;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.dto.message.ReceiverResponseDto;
import com.santho.lms.services.chat.ChatUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatUserController {
    private final ChatUserService chatUserService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/user.connect")
    @SendTo("/topic/public")
    public MessagesResponseDto connectUser(Principal principal) {
        return chatUserService.connectUser(principal.getName());
    }

    @MessageMapping("/user.disconnect")
    @SendTo("/topic/public")
    public MessagesResponseDto disconnectUser(Principal principal, @Payload MessagesRequestDto receiver) {
        if(receiver.getReceiver().isEmpty())
            return chatUserService.disconnectUser(principal.getName(), null);
        messagingTemplate.convertAndSendToUser(receiver.getReceiver(), "/queue/messages", ReceiverResponseDto.builder()
                .name("")
                .email("")
                .messageType(MessageType.ASSIGNED)
                .build());
        return chatUserService.disconnectUser(principal.getName(), receiver.getReceiver().isEmpty() ? null : receiver.getReceiver());
    }

    @MessageMapping("/user.assign")
    public void assignAdmin(Principal principal) {
        try {
            ReceiverResponseDto[] receiverArray = chatUserService.assignAdmin(principal.getName());
            messagingTemplate.convertAndSendToUser(receiverArray[1].getEmail(), "/queue/messages", receiverArray[0]);
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", receiverArray[1]);
        } catch (NullPointerException ex) {
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", ReceiverResponseDto.builder().messageType(MessageType.FAILED).name("No admins currently available").build());
        }
    }

    @MessageMapping("/user.endchat")
    public void endChat(Principal principal, @Payload MessagesRequestDto receiver) {
        chatUserService.endChat(principal.getName(), receiver.getReceiver());
        messagingTemplate.convertAndSendToUser(receiver.getReceiver(), "/queue/messages", ReceiverResponseDto.builder()
                .name("")
                .email("")
                .messageType(MessageType.ASSIGNED)
                .build());
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", ReceiverResponseDto.builder()
                .name("")
                .email("")
                .messageType(MessageType.ASSIGNED)
                .build());
    }

}
