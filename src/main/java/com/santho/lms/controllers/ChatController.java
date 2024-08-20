package com.santho.lms.controllers;

import com.santho.lms.dto.message.MessagesRequestDto;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.services.BorrowerService;
import com.santho.lms.services.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/all-chat")
    @SendTo("/topic/public")
    public MessagesResponseDto allChat(@Payload MessagesRequestDto request, Principal principal) {
        return messageService.send(principal.getName(), request);
    }

    @MessageMapping("/private-chat")
    public void privateChat(@Payload MessagesRequestDto request, Principal principal) {
        MessagesResponseDto saved = messageService.send(principal.getName(), request);
        messagingTemplate.convertAndSendToUser(request.getReceiver(), "/queue/messages", saved);
    }

    @GetMapping("/messages/{receiver}")
    private ResponseEntity<List<MessagesResponseDto>> getAllMessages(Principal principal, @PathVariable String receiver) {
        return ResponseEntity.ok(messageService.findMessages(principal.getName(), receiver));
    }
}
