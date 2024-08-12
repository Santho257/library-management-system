package com.santho.lms.services.chat;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.daos.MessageDao;
import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.dto.message.MessagesRequestDto;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.mappers.MessageMapper;
import com.santho.lms.models.Borrower;
import com.santho.lms.models.Messages;
import com.santho.lms.models.Role;
import com.santho.lms.models.Status;
import com.santho.lms.services.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ModelMapper modelMapper;
    private final ChatRoomService chatRoomService;
    private final BorrowerDao borrowerDao;
    private final BorrowerService borrowerService;
    private final MessageDao repository;

    public MessagesResponseDto send(String sender, MessagesRequestDto request){
        Messages saved = repository.save(MessageMapper.toMessage(request,sender));
        return MessageMapper.toMessageResponse(saved);
    }

    public List<MessagesResponseDto> findMessages(String sender, String receiver){
        Optional<String> chatId = chatRoomService.getRoomId(sender,receiver,false);
        return chatId
                .map(repository::findAllByChatId)
                .orElse(new ArrayList<>())
                .stream()
                .map(MessageMapper::toMessageResponse).toList();
    }
}
