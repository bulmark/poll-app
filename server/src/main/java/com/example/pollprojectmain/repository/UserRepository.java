package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Page<User> findAll(Pageable pageable);
    List<User> findAll();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
