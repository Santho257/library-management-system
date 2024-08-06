package com.santho.lms.controllers;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.book.BookRequestDto;
import com.santho.lms.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping
    public ResponseEntity<Response<?>> add(@Valid @RequestBody BookRequestDto book, BindingResult res){
        return bookService.add(book,res);
    }
    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        return bookService.getAll();
    }
    @GetMapping("/sort")
    public ResponseEntity<Response<?>> sort(@RequestParam(required = false) String by){
        return bookService.sort(by);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<?>> search(@RequestParam String key, @RequestParam(required = false) String by){
        return bookService.search(key.toLowerCase(), by);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        return bookService.delete(id);
    }
}
