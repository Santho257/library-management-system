package com.santho.lms.services.chat;

import com.santho.lms.daos.ChatRoomDao;
import com.santho.lms.models.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    public final ChatRoomDao repository;

    public Optional<String> getRoomId(String sender, String receiver, boolean createIfNotExists) {
        return repository.findBySenderAndReceiver(sender, receiver)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createIfNotExists) {
                        String chatId = createChatId(sender, receiver);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String sender, String receiver) {
        String chatId = String.format("%s_%s",sender,receiver);
        repository.save(buildChatRoom(sender,receiver,chatId));
        repository.save(buildChatRoom(receiver,sender,chatId));
        return chatId;
    }

    private ChatRoom buildChatRoom(String sender, String receiver, String chatId){
        return ChatRoom.builder()
                .chatId(chatId)
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
