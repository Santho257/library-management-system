package com.santho.lms.daos;

import com.santho.lms.models.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowerDao extends JpaRepository<Borrower, String> {
    @Query("SELECT br FROM Borrower br ORDER BY br.name")
    List<Borrower> sort();
    @Query("SELECT br FROM Borrower br WHERE LOWER(br.name) LIKE %:key%")
    List<Borrower> search(String key);
}
