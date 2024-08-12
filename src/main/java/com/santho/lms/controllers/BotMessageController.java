package com.santho.lms.controllers;

import com.santho.lms.dto.botmessage.BotMessageRequestDto;
import com.santho.lms.dto.botmessage.BotMessageResponseDto;
import com.santho.lms.services.chat.BotMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bot")
public class BotMessageController {
    private final BotMessageService botMessageService;
    @PostMapping
    public ResponseEntity<String> addMessage(BotMessageRequestDto request){
        return ResponseEntity.ok(botMessageService.addMessage(request));
    }
    @GetMapping
    public ResponseEntity<List<BotMessageResponseDto>> allMessages(){
        return ResponseEntity.ok(botMessageService.allMessages());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BotMessageResponseDto> getById(@PathVariable String id){
        return ResponseEntity.ok(botMessageService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id){
        botMessageService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
