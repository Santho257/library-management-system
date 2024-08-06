package com.santho.lms.services;

import com.santho.lms.dto.author.AuthorRequestDto;
import com.santho.lms.dto.Response;
import com.santho.lms.models.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public interface AuthorService {
    ResponseEntity<Response<?>> addAuthor(AuthorRequestDto authorRequestDto, BindingResult res);

    ResponseEntity<Response<?>> getAll();

    Author get(int id);

    ResponseEntity<Response<?>> sort(String by);

    ResponseEntity<Response<?>> search(String key);

    ResponseEntity<Response<?>> delete(int id);
}
