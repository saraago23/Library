package com.project.library.repository;

import com.project.library.entity.PatronEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<PatronEntity, Integer> {

    Optional<PatronEntity> findByIdAndDeletedFalse(Integer id);
    @Query("SELECT p FROM PatronEntity p WHERE p.deleted=false ")
    Page<PatronEntity> findAllNonDeleted(Pageable pageable);

    @Query("SELECT p FROM PatronEntity p WHERE p.deleted=false ")
    List<PatronEntity> findAllNonDeleted();

}
