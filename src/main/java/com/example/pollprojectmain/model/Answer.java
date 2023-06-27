package com.example.pollprojectmain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes = new ArrayList<>();

    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Integer votesCount;

    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Double percent;

    @Column(nullable = false)
    String text;

    public Answer produceCopyTo(Question question) {
        Answer copyAnswer = new Answer();
        copyAnswer.setQuestion(question);
        copyAnswer.setText(this.getText());
        return copyAnswer;
    }
}
