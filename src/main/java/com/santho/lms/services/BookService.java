package com.santho.lms.services;

import com.santho.lms.dto.Response;
import com.santho.lms.dto.book.BookRequestDto;
import com.santho.lms.models.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public interface BookService {
    ResponseEntity<Response<?>> add(BookRequestDto book, BindingResult res);

    ResponseEntity<Response<?>> getAll();

    ResponseEntity<Response<?>> sort(String by);

    ResponseEntity<Response<?>> search(String key, String by);

    ResponseEntity<Response<?>> delete(int id);

    Book get(int id);
}
