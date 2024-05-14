package com.project.library.repository;

import com.project.library.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {
    Optional<BookEntity> findByIdAndDeletedFalse(Integer id);
    BookEntity findBookEntityByIsbn(String isbn);

    @Query("SELECT b FROM BookEntity b WHERE b.deleted=false ")
    Page<BookEntity> findAllNonDeleted(Pageable pageable);
    @Query("SELECT b FROM BookEntity b WHERE b.deleted=false ")
    List<BookEntity> findAllNonDeleted();

}
