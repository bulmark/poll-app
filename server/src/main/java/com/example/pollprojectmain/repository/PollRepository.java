package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {
    List<Poll> findPollsByOwner(User owner);
}
