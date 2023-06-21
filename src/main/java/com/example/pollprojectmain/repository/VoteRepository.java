package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Answer;
import com.example.pollprojectmain.model.User;
import com.example.pollprojectmain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Boolean existsByAnswerAndUser(Answer answer, User user);
}
