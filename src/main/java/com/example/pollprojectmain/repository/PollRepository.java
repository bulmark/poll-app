package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Integer> {
    List<Poll> getPollsByOwner(User user);

}
