package com.santho.lms.services.chat;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.dto.message.MessageType;
import com.santho.lms.dto.message.MessagesResponseDto;
import com.santho.lms.dto.message.ReceiverResponseDto;
import com.santho.lms.mappers.ReceiverResponseMapper;
import com.santho.lms.models.Borrower;
import com.santho.lms.models.Role;
import com.santho.lms.models.Status;
import com.santho.lms.services.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUserService {
    private final BorrowerService borrowerService;
    private final BorrowerDao borrowerDao;
    private final ModelMapper modelMapper;

    public MessagesResponseDto connectUser(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.BOTCHAT);
        borrowerDao.save(user);
        return MessagesResponseDto.builder()
                .messageType(MessageType.CONNECTED)
                .sender(name)
                .sentAt(LocalDateTime.now().toString())
                .build();
    }

    public MessagesResponseDto disconnectUser(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.DISCONNECTED);
        borrowerDao.save(user);
        return MessagesResponseDto.builder()
                .messageType(MessageType.DISCONNECTED)
                .sender(name)
                .sentAt(LocalDateTime.now().toString())
                .build();
    }


    public ReceiverResponseDto[] assignAdmin(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.ASSIGNED);
        borrowerDao.save(user);
        List<Borrower> adminList = borrowerDao.findByRoleAndStatus(Role.ADMIN, Status.BOTCHAT);
        if(adminList.isEmpty()) throw new NullPointerException("List is Empty!!");
        Borrower admin = adminList.get((int) (Math.random() * adminList.size()));
        admin.setStatus(Status.ASSIGNED);
        borrowerDao.save(admin);
        return new ReceiverResponseDto[]{ReceiverResponseMapper.toRecieverReponse(user), ReceiverResponseMapper.toRecieverReponse(admin)};
    }
}
