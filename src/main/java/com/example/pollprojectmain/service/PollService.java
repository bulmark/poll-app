package com.example.pollprojectmain.service;

import com.example.pollprojectmain.model.Poll;
import com.example.pollprojectmain.repository.PollRepository;
import com.example.pollprojectmain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private PollRepository pollRepository;
    private UserRepository userRepository;

    @Autowired
    public PollService(PollRepository pollRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }
    public Optional<Poll> findById(Integer id) {
        return pollRepository.findById(id);
    }

    public void create(Poll poll) {
        pollRepository.save(poll);
    }

}
