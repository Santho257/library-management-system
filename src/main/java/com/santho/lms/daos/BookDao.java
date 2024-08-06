package com.santho.lms.daos;

import com.santho.lms.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b ORDER BY b.title")
    List<Book> sort();

    @Query("SELECT b FROM Book b ORDER BY b.author.name")
    List<Book> sortByAuthor();

    @Query("SELECT b FROM Book b ORDER BY b.publicationDate")
    List<Book> sortByPublicationdate();

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:key%")
    List<Book> search(String key);

    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) LIKE :key%")
    List<Book> searchByGenre(String key);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE %:key%")
    List<Book> searchByAuthor(String key);
}
