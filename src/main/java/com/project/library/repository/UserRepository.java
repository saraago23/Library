package com.project.library.repository;

import com.project.library.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByIdAndDeletedFalse(Integer id);

    @Query("SELECT u FROM UserEntity u WHERE u.deleted=false ")
    Page<UserEntity> findAllNonDeleted(Pageable pageable);
    Optional<UserEntity> findByUsernameAndDeletedFalse(String username);


}