package com.santho.lms.controllers;

import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.dto.message.MessagesRequestDto;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.models.Role;
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
    private final BorrowerService borrowerService;

    @MessageMapping("/all-chat")
    @SendTo("/topic/public")
    public MessagesResponseDto allChat(@Payload MessagesRequestDto request, Principal principal) {
        return messageService.send(principal.getName(), request);
    }

    @MessageMapping("/private-chat")
    public void privateChat(@Payload MessagesRequestDto request, Principal principal) {
        Role receiverRole = borrowerService.get(request.getReceiver()).getRole();
        Role senderRole = borrowerService.get(principal.getName()).getRole();
        MessagesResponseDto saved = messageService.send(principal.getName(), request);
        if (receiverRole.equals(Role.BORROWER) && senderRole.equals(Role.ADMIN))
            messagingTemplate.convertAndSendToUser(request.getReceiver(), "/admin/messages", saved);
        else if (receiverRole.equals(Role.BORROWER) && senderRole.equals(Role.BOT)) {
            messagingTemplate.convertAndSendToUser(request.getReceiver(), "/bot/messages", saved);
        } else
            messagingTemplate.convertAndSendToUser(request.getReceiver(), "/borrower/messages", saved);
    }

    @GetMapping("/messages/{receiver}")
    private ResponseEntity<List<MessagesResponseDto>> getAllMessages(Principal principal, @PathVariable String receiver) {
        return ResponseEntity.ok(messageService.findMessages(principal.getName(), receiver));
    }
}
