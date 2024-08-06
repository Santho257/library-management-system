package com.santho.lms.services;

import com.santho.lms.daos.BorrowerDao;
import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrower.BorrowerRequestDto;
import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.mappers.BorrowMapper;
import com.santho.lms.models.Borrower;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService{
    @Autowired
    private BorrowerDao borrowerDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<Response<String>> add(BorrowerRequestDto borrower) {
        if(borrowerDao.existsById(borrower.getUsername()))
            return ResponseEntity.badRequest().body(new Response<>("Borrower Already Exists with the username", HttpStatusCode.valueOf(400)));
        borrowerDao.save(BorrowMapper.map(borrower));
        return ResponseEntity.ok(new Response<>("Borrower Created Successfully",HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<List<BorrowerResponseDto>>> getAll() {
        List<BorrowerResponseDto> borrowers = borrowerDao.findAll().stream()
                .map(br -> modelMapper.map(br, BorrowerResponseDto.class))
                .toList();
        return ResponseEntity.ok(new Response<>(borrowers, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> sort(){
        List<BorrowerResponseDto> borrowers = borrowerDao.sort().stream()
                .map(br -> modelMapper.map(br, BorrowerResponseDto.class))
                .toList();
        return ResponseEntity.ok(new Response<>(borrowers, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> search(String key) {
        List<BorrowerResponseDto> borrowers = borrowerDao.search(key).stream()
                .map(br -> modelMapper.map(br, BorrowerResponseDto.class))
                .toList();
        if(borrowers.isEmpty())
            return ResponseEntity.status(404).body(new
                    Response<>("No borrowers find with the name: "+key, HttpStatusCode.valueOf(404)));
        return ResponseEntity.ok(new Response<>(borrowers, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<String>> delete(String id) {
        if(!borrowerDao.existsById(id)){
            return ResponseEntity.status(404)
                    .body(new Response<>("User doesnot exists by username: "+id,HttpStatusCode.valueOf(404)));
        }
        borrowerDao.deleteById(id);
        return ResponseEntity
                .ok(new Response<>("User deleted by username: "+id,HttpStatusCode.valueOf(200)));
    }

    @Override
    public Borrower get(String username) {
        return borrowerDao.findById(username)
                .orElseThrow(() -> new NullPointerException("Borrower Not available with username : "+username));
    }

}
