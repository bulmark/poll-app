package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Integer> {

}
