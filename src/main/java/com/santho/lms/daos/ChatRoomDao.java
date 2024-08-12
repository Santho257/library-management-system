package com.santho.lms.daos;

import com.santho.lms.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomDao extends MongoRepository<ChatRoom,String > {
    Optional<ChatRoom> findBySenderAndReceiver(String sender, String receiver);
}
