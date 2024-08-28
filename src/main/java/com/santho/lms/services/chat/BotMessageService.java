package com.santho.lms.services.chat;

import com.santho.lms.daos.BotMessagesDao;
import com.santho.lms.dto.botmessage.BotMessageRequestDto;
import com.santho.lms.dto.botmessage.BotMessageResponseDto;
import com.santho.lms.mappers.BotMessagesMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotMessageService {
    private final BotMessagesDao messagesDao;

    public String addMessage(BotMessageRequestDto request) {
        return messagesDao.save(BotMessagesMapper.toBotMessage(request)).getId();
    }

    public List<BotMessageResponseDto> allMessages() {
        return messagesDao.findAll().stream()
                .map(BotMessagesMapper::toBotMessageResponse)
                .toList();
    }


    public String delete(String id) {
        if(!messagesDao.existsById(id))
            throw new EntityNotFoundException("No Question found :: Question Id : " + id);
        messagesDao.deleteById(id);
        return "Question deleted with Id :: "+id;
    }

    public BotMessageResponseDto findById(String id) {
        return messagesDao.findById(id).map(BotMessagesMapper::toBotMessageResponse)
                .orElseThrow(() -> new EntityNotFoundException("No Question found :: Question Id : " + id));
    }
}
