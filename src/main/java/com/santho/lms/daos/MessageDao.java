package com.santho.lms.daos;

import com.santho.lms.models.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageDao extends MongoRepository<Messages, String > {
    List<Messages> findAllByChatId(String chatId);
}
