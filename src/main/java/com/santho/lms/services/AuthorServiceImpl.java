package com.santho.lms.services;

import com.santho.lms.daos.AuthorDao;
import com.santho.lms.dto.author.AuthorRequestDto;
import com.santho.lms.dto.author.AuthorResponseCountDto;
import com.santho.lms.dto.author.AuthorResponseDto;
import com.santho.lms.dto.Response;
import com.santho.lms.mappers.AuthorMapper;
import com.santho.lms.models.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthorDao authorDao;
    @Override
    public ResponseEntity<Response<?>> addAuthor(AuthorRequestDto authorRequestDto, BindingResult res) {
        if(res.hasErrors()){
            try {
                return ResponseEntity.badRequest().body(new Response<>("name "+res.getFieldError("name").getDefaultMessage(), HttpStatusCode.valueOf(400)));
            }
            catch (NullPointerException ex){
                return ResponseEntity.internalServerError().body(new Response<>(ex.getMessage(), HttpStatusCode.valueOf(500)));
            }
        }
        Author author = modelMapper.map(authorRequestDto, Author.class);
        authorDao.save(author);
        return ResponseEntity.ok(new Response<>("Author Saved", HttpStatusCode.valueOf(201)));
    }

    @Override
    public ResponseEntity<Response<?>> getAll() {
        List<AuthorResponseDto> authors = authorDao.findAll().stream()
                .map(auth -> modelMapper.map(auth, AuthorResponseDto.class))
                .toList();
        return ResponseEntity.ok(new Response<>(authors, HttpStatusCode.valueOf(200)));
    }

    @Override
    public Author get(int id) {
        return authorDao.getReferenceById(id);
    }

    @Override
    public ResponseEntity<Response<?>> sort(String by) {
        List<AuthorResponseCountDto> authors;
        if(by == null)
            authors = authorDao.sort()
                    .stream()
                    .map(AuthorMapper::revCountMap)
                    .toList();
        else if(by.equalsIgnoreCase("booksWritten"))
            authors = authorDao.sortByBooks()
                    .stream()
                    .map(AuthorMapper::revCountMap)
                    .toList();
        else
            authors = new ArrayList<>();

        return ResponseEntity.ok(new Response<>(authors, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> search(String key) {
        List<AuthorResponseDto> authors = authorDao.findByNameSearch(key)
                .stream()
                .map(au -> modelMapper.map(au, AuthorResponseDto.class))
                .toList();
        return (authors.isEmpty()) ? ResponseEntity.status(404).body(new Response<>("No Authors Found with the key", HttpStatusCode.valueOf(404)))
                : ResponseEntity.ok(new Response<>(authors, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> delete(int id) {
        if(!authorDao.existsById(id)){
            return ResponseEntity.status(404).body(new Response<>("No author found By Id: "+id,HttpStatusCode.valueOf(404)));
        }
        authorDao.deleteById(id);
        return ResponseEntity.ok(new Response<>("Author Deleted By Id : " + id,HttpStatusCode.valueOf(200)));
    }
}
