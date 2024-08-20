package com.santho.lms.controllers;

import com.santho.lms.dto.message.MessageType;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.dto.message.ReceiverResponseDto;
import com.santho.lms.services.chat.ChatUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatUserController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ChatUserService chatUserService;

    @MessageMapping("/user.connect")
    @SendTo("/topic/public")
    public MessagesResponseDto connectUser(Principal principal) {
        return chatUserService.connectUser(principal.getName());
    }

    @MessageMapping("/user.disconnect")
    @SendTo("/topic/public")
    public MessagesResponseDto disconnectUser(Principal principal) {
        System.out.println("Inside Disconnect");
        return chatUserService.disconnectUser(principal.getName());
    }

    @MessageMapping("/user.assign")
    public void assignAdmin(Principal principal) {
        try {
            ReceiverResponseDto[] receiverArray = chatUserService.assignAdmin(principal.getName());
            messagingTemplate.convertAndSendToUser(receiverArray[1].getEmail(), "/queue/messages", receiverArray[0]);
            messagingTemplate.convertAndSendToUser( principal.getName(),"/queue/messages", receiverArray[1]);
        }
        catch (NullPointerException ex){
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/messages", ReceiverResponseDto.builder().messageType(MessageType.FAILED) .name("No admins currently available").build());
        }
    }

}
