package com.example.pollprojectmain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "polls")
@Getter
@Setter
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
    @Column(name = "voting_time ")
    private Duration votingTime;
    @Column(name = "up_to_date")
    private Timestamp upToDate;
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Spectator> spectators = new ArrayList<>();

    public Poll(User owner,
                String text,
                Duration period,
                Timestamp upToDate,
                Timestamp createAt,
                List<Question> questions) {

        this.owner = owner;
        this.text = text;
        this.period = period;
        this.upToDate = upToDate;
        this.createAt = createAt;
        this.questions = questions;

    }
}
