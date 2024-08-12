package com.santho.lms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("chat_room")
public class ChatRoom {
    @Id
    public String id;
    public String sender;
    public String receiver;
    public String chatId;
}
