package com.santho.lms.services;

import com.santho.lms.daos.BookDao;
import com.santho.lms.dto.Response;
import com.santho.lms.dto.book.BookRequestDto;
import com.santho.lms.dto.book.BookResponseDto;
import com.santho.lms.mappers.BookMapper;
import com.santho.lms.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;

    @Override
    public ResponseEntity<Response<?>> add(BookRequestDto book, BindingResult res) {
        if(res.hasErrors()){
            try {
                return ResponseEntity.badRequest().body(new Response<>("publicationDate "+res.getFieldError("publicationDate").getDefaultMessage(), HttpStatusCode.valueOf(400)));
            }
            catch (NullPointerException ex){
                return ResponseEntity.internalServerError().body(new Response<>(ex.getMessage(), HttpStatusCode.valueOf(500)));
            }
        }
        Book bookObj = BookMapper.map(book);
        try {
            bookDao.save(bookObj);
        }
        catch (Exception ex){
            return ResponseEntity.internalServerError().body(new Response<>(ex.getMessage(), HttpStatusCode.valueOf(500)));
        }
        return ResponseEntity.status(201).body(new Response<>("Book Saved", HttpStatusCode.valueOf(201)));
    }
    @Override
    public ResponseEntity<Response<?>> getAll() {
        return ResponseEntity.ok(new Response<>(bookDao.findAll().stream()
                .map(BookMapper::revMap)
                .toList(), HttpStatusCode.valueOf(200)));
    }
    @Override
    public ResponseEntity<Response<?>> sort(String by) {
        List<BookResponseDto> books;
        if(by == null)
            books = bookDao.sort().stream()
                    .map(BookMapper::revMap)
                    .toList();
        else if(by.equalsIgnoreCase("author"))
            books = bookDao.sortByAuthor().stream()
                    .map(BookMapper::revMap)
                    .toList();
        else if(by.equalsIgnoreCase("pub-date"))
            books = bookDao.sortByPublicationdate().stream()
                    .map(BookMapper::revMap)
                    .toList();
        else
            books = new ArrayList<>();
        return ResponseEntity.ok(new Response<>(books, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> search(String key, String by) {
        List<BookResponseDto> books;
        if(by == null)
            books = bookDao.search(key).stream()
                    .map(BookMapper::revMap)
                    .toList();
        else if(by.equalsIgnoreCase("genre"))
            books = bookDao.searchByGenre(key).stream()
                    .map(BookMapper::revMap)
                    .toList();
        else if (by.equalsIgnoreCase("author"))
            books = bookDao.searchByAuthor(key).stream()
                    .map(BookMapper::revMap)
                    .toList();
        else
            books = new ArrayList<>();
        return ResponseEntity.ok(new Response<>(books, HttpStatusCode.valueOf(200)));
    }

    @Override
    public ResponseEntity<Response<?>> delete(int id) {
        if(!bookDao.existsById(id)){
            return ResponseEntity.badRequest().body(new Response<>("Book Not Found With Id : "+id, HttpStatusCode.valueOf(400)));
        }
        bookDao.deleteById(id);
        return ResponseEntity.ok(new Response<>("Book Deleted With Id : "+id, HttpStatusCode.valueOf(200)));
    }

    @Override
    public Book get(int id) {
        if(!bookDao.existsById(id))
            throw new NullPointerException("Book is not available with ID : " + id);
        return bookDao.getReferenceById(id);
    }
}
