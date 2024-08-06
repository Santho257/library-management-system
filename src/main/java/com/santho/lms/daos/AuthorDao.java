package com.santho.lms.daos;

import com.santho.lms.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDao extends JpaRepository<Author,Integer> {
    @Query("SELECT a FROM Author a ORDER BY a.name")
    List<Author> sort();

    @Query("SELECT a, COUNT(b.id) AS books FROM Author a JOIN Book b ON b.author.id = a.id GROUP BY a ORDER BY books DESC")
    List<Author> sortByBooks();

    @Query("SELECT a FROM Author a WHERE a.name LIKE %:key%")
    List<Author> findByNameSearch(String key);
}
