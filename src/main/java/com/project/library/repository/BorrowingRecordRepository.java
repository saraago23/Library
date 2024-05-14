package com.project.library.repository;

import com.project.library.entity.BookEntity;
import com.project.library.entity.BorrowingRecordEntity;
import com.project.library.entity.PatronEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecordEntity, Integer> {
    Optional<BorrowingRecordEntity> findByIdAndDeletedFalse(Integer id);

    List<BorrowingRecordEntity> findBorrowingRecordEntityByPatronAndReturnDate(PatronEntity patron, LocalDate date);

    BorrowingRecordEntity findBorrowingRecordEntityByPatronId(Integer patronId);

    @Query("SELECT br FROM BorrowingRecordEntity br WHERE br.deleted=false ")
    Page<BorrowingRecordEntity> findAllNonDeleted(Pageable pageable);

    @Query("SELECT br FROM BorrowingRecordEntity br WHERE br.returnDate=:date")
    BorrowingRecordEntity findBorrowingRecordEntityByReturnDate(LocalDate date);

    Optional<BorrowingRecordEntity> findFirstByBookAndPatronAndReturnDateIsNull(BookEntity bookToBeReturned, PatronEntity patron);
}
