package com.example.pollprojectmain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "polls")
@Data
@NoArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    @Column(nullable = false)
    private String text;

    @Type(PostgreSQLIntervalType.class)
    @Column(columnDefinition = "interval")
    private Duration period;
    @Column(name = "up_to_date")
    private LocalDate upToDate;
    @Column(nullable = false)
    private LocalDate createAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    public Poll(User owner,
                String text,
                Duration period,
                LocalDate upToDate,
                LocalDate createAt,
                List<Question> questions) {

        this.owner = owner;
        this.text = text;
        this.period = period;
        this.upToDate = upToDate;
        this.createAt = createAt;
        this.questions = questions;

    }
}
