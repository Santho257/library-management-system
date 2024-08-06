package com.santho.lms.services;

import com.santho.lms.daos.BorrowDetailsDao;
import com.santho.lms.daos.LibraryDao;
import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrowdetails.BorrowDetailsRequestDto;
import com.santho.lms.dto.borrowdetails.BorrowDetailsResponseDto;
import com.santho.lms.exception.AlreadReturnedException;
import com.santho.lms.mappers.BorrowDetailsMapper;
import com.santho.lms.models.Borrower;
import com.santho.lms.models.BorrowerDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowDetailsServiceImpl implements BorrowDetailsService{
    private final BorrowDetailsDao borrowDetailsDao;
    private final LibraryDao libraryDao;
    @Override
    public ResponseEntity<Response<?>> getAll() {
        List<BorrowDetailsResponseDto> borrowDetails = borrowDetailsDao.findAll().stream()
                .map(BorrowDetailsMapper::revMap)
                .toList();
        return ResponseEntity.ok(new Response<>(borrowDetails, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<String>> borrow(BorrowDetailsRequestDto bdrDto) {
        try {
            borrowDetailsDao.save(BorrowDetailsMapper.map(bdrDto));
        }
        catch (NullPointerException ex){
            return ResponseEntity.status(404).body(
                    new Response<>(ex.getMessage(),HttpStatusCode.valueOf(404)));
        }
        return ResponseEntity.status(201).body(
                new Response<>("Borrowed the book",HttpStatusCode.valueOf(201)));
    }

    @Override
    public ResponseEntity<Response<String>> returnn(String username, int id) {
        BorrowerDetails borrowerDetails;
        borrowerDetails = libraryDao.findByBorrowerAndBookId(username, id);
        if(borrowerDetails == null)
            throw new EntityNotFoundException("No Data available for given data");
        if(borrowerDetails.getReturnedOn() != null)
            throw new AlreadReturnedException("Book Already Returned :: Book Id : " + id);
        borrowerDetails.setReturnedOn(LocalDate.now());
        borrowDetailsDao.save(borrowerDetails);
        return ResponseEntity.status(200).body(
                new Response<>("Book Returned Successfully",HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> getByBorrower(String username) {
        List<BorrowDetailsResponseDto> borrowDetails;
        try {
            borrowDetails = borrowDetailsDao.findByBorrower(username)
                    .stream()
                    .map(BorrowDetailsMapper::revMap)
                    .toList();
        }
        catch (NullPointerException ex){
            return ResponseEntity.status(400).body(
                    new Response<>(ex.getMessage(),HttpStatusCode.valueOf(400)));
        }
        return ResponseEntity.status(200).body(
                new Response<>(borrowDetails,HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> unBrReturned(String username) {
        List<BorrowDetailsResponseDto> borrowDetails;
        try{
            borrowDetails = borrowDetailsDao.findByBorrower(username)
                    .stream()
                    .filter(br -> br.getReturnedOn() == null)
                    .map(BorrowDetailsMapper::revMap)
                    .toList();
        }
        catch (NullPointerException ex){
            return ResponseEntity.status(400).body(
                    new Response<>(ex.getMessage(),HttpStatusCode.valueOf(400)));
        }
        return ResponseEntity.status(200).body(
                new Response<>(borrowDetails,HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> unReturned() {
        List<BorrowDetailsResponseDto> borrowDetails;
        try{
            borrowDetails = borrowDetailsDao.findAll()
                    .stream()
                    .filter(bd -> bd.getReturnedOn() == null)
                    .map(BorrowDetailsMapper::revMap)
                    .toList();
        }
        catch (NullPointerException ex){
            return ResponseEntity.status(400).body(
                    new Response<>(ex.getMessage(),HttpStatusCode.valueOf(400)));
        }
        return ResponseEntity.status(200).body(
                new Response<>(borrowDetails,HttpStatusCode.valueOf(200)));
    }
}
