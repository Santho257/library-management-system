package com.santho.lms.services.chat;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.models.Borrower;
import com.santho.lms.models.Role;
import com.santho.lms.models.Status;
import com.santho.lms.services.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUserService {
    private final BorrowerService borrowerService;
    private final BorrowerDao borrowerDao;
    private final ModelMapper modelMapper;
    public BorrowerResponseDto connectUser(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.BOTCHAT);
        Borrower updated = borrowerDao.save(user);
        return modelMapper.map(updated, BorrowerResponseDto.class);
    }

    public BorrowerResponseDto disconnectUser(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.DISCONNECTED);
        Borrower updated = borrowerDao.save(user);
        return modelMapper.map(updated, BorrowerResponseDto.class);
    }


    public BorrowerResponseDto assignAdmin(String name) {
        Borrower user = borrowerService.get(name);
        user.setStatus(Status.ASSIGNED);
        borrowerDao.save(user);
        List<Borrower> adminList = borrowerDao.findByRoleAndStatus(Role.ADMIN, Status.BOTCHAT);
        Borrower admin = adminList.get((int)(Math.random() * adminList.size()));
        admin.setStatus(Status.ASSIGNED);
        Borrower updated = borrowerDao.save(admin);
        return modelMapper.map(updated, BorrowerResponseDto.class);
    }
}
