package com.santho.lms.daos;

import com.santho.lms.models.BorrowerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowDetailsDao extends JpaRepository<BorrowerDetails, Integer> {
    @Query("SELECT b FROM BorrowerDetails b WHERE LOWER(b.borrower.username) LIKE %:username%")
    List<BorrowerDetails> findByBorrower(String username);
}
