package com.santho.lms.controllers;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.borrower.BorrowerRequestDto;
import com.santho.lms.dto.borrower.BorrowerResponseDto;
import com.santho.lms.services.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrowers")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowerService;
    @PostMapping
    public ResponseEntity<Response<String>> add(@RequestBody BorrowerRequestDto borrower){
        return borrowerService.add(borrower);
    }
    @GetMapping
    public ResponseEntity<Response<List<BorrowerResponseDto>>> getAll(){
        return borrowerService.getAll();
    }

    @GetMapping("/sort")
    public ResponseEntity<Response<?>> sort(){
        return borrowerService.sort();
    }
    @GetMapping("/search")
    public ResponseEntity<Response<?>> search(@RequestParam String key){
        return borrowerService.search(key.toLowerCase());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable String id){
        return borrowerService.delete(id);
    }

}
