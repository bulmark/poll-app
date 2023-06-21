package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Spectator;
import com.example.pollprojectmain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpectatorRepository extends JpaRepository<Spectator, Integer> {
    List<Spectator> findSpectatorsByUser(User user);
}
