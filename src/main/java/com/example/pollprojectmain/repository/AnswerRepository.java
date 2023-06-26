package com.example.pollprojectmain.repository;

import com.example.pollprojectmain.model.Answer;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByIdIn(List<Integer> ids);
}
