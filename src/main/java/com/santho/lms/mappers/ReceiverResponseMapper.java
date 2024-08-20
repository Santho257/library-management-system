package com.santho.lms.mappers;

import com.santho.lms.dto.message.MessageType;
import com.santho.lms.dto.message.ReceiverResponseDto;
import com.santho.lms.models.Borrower;
import org.springframework.stereotype.Service;

@Service
public class ReceiverResponseMapper {
    public static ReceiverResponseDto toRecieverReponse(Borrower borrower){
        return ReceiverResponseDto.builder()
                .email(borrower.getUsername())
                .status(borrower.getStatus())
                .name(borrower.getName())
                .messageType(MessageType.ASSIGNED)
                .build();
    }
}
