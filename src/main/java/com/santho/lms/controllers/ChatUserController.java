package com.santho.lms.controllers;

import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.services.chat.ChatUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatUserController {
    private final ChatUserService chatUserService;


    @MessageMapping("/user.connect")
    @SendTo("/topic/public")
    public BorrowerResponseDto connectUser(Principal principal) {
        return chatUserService.connectUser(principal.getName());
    }

    @MessageMapping("/user.disconnect")
    @SendTo("/topic/public")
    public BorrowerResponseDto disconnectUser( Principal principal) {
        return chatUserService.disconnectUser(principal.getName());
    }

    @MessageMapping("/user.assign")
    @SendTo("/topic/public")
    public BorrowerResponseDto assignAdmin(Principal principal) {
        return chatUserService.assignAdmin(principal.getName());
    }

}
