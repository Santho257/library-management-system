package com.santho.lms.daos;

import com.santho.lms.models.BorrowerDetails;
import com.santho.lms.services.BorrowerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LibraryDao {
    private final EntityManager em;
    private final BorrowerService borrowerService;
    private final BookDao bookDao;
    public BorrowerDetails findByBorrowerAndBookId(String username, Integer bookId){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BorrowerDetails> criteriaQuery = criteriaBuilder.createQuery(BorrowerDetails.class);
        Root<BorrowerDetails> root = criteriaQuery.from(BorrowerDetails.class);
        Predicate byUsername = criteriaBuilder.equal(root.get("borrower"), borrowerService.get(username));
        Predicate byBookId = criteriaBuilder.equal(root.get("book"), bookDao.findById(bookId));
        Predicate byUserBook = criteriaBuilder.and(byUsername, byBookId);
        criteriaQuery.where(byUserBook);
        TypedQuery<BorrowerDetails> finalQuery = em.createQuery(criteriaQuery);
        return finalQuery.getSingleResult();
    }
}
