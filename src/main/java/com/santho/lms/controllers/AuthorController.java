package com.santho.lms.controllers;

import com.santho.lms.dto.author.AuthorRequestDto;
import com.santho.lms.dto.Response;
import com.santho.lms.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    public AuthorService authorService;
    @PostMapping
    public ResponseEntity<Response<?>> add(@Valid @RequestBody AuthorRequestDto authorRequestDto, BindingResult res){
        return authorService.addAuthor(authorRequestDto, res);
    }
    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        return authorService.getAll();
    }
    @GetMapping("/sort")
    public ResponseEntity<Response<?>> sort(@RequestParam(required = false) String by){
        return authorService.sort(by);
    }
    @GetMapping("/search")
    public ResponseEntity<Response<?>> search(@RequestParam String key){
        return authorService.search(key.toUpperCase());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        return authorService.delete(id);
    }
}
