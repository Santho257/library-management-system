package com.santho.lms.daos;

import com.santho.lms.models.BotMessages;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BotMessagesDao extends MongoRepository<BotMessages, String> {
}
