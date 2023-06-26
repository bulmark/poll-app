package com.example.pollprojectmain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Schema(
            example = "P20D"
    )
    @Type(PostgreSQLIntervalType.class)
    private Duration period;


    @Schema(
            example = "PT1H"
    )
    @Column(name = "voting_time ")
    private Duration votingTime;

    @Column(name = "up_to_date")
    private Timestamp upToDate;
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
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

    public Poll(Poll poll) {
        this.setOwner(poll.getOwner());
        this.setText(poll.getText());
        this.setPeriod(poll.getPeriod());
        this.setUpToDate(poll.getUpToDate());

        this.setSpectators(poll.getSpectators());
        for(Spectator spectator : spectators) {
            spectator.setPoll(this);
        }

        this.setQuestions(poll.getQuestions());
        for(Question question : questions) {
            question.setId(null);
            question.setPoll(this);
            for (Answer answer : question.getAnswers()) {
                answer.setId(null);
            }
        }



        nowCreateAt();

    }

    @JsonIgnore
    public Boolean isOver() {

        if (votingTime == null) {
            return false;
        }

        var currentDateTime = Timestamp.valueOf(LocalDateTime.now());
        var timeOfVoteEnding = Timestamp.from(getCreateAt().toInstant().plus(votingTime));

        if (currentDateTime.before(timeOfVoteEnding)) {
            return false;
        }

        return true;
    }

    @JsonIgnore
    public Boolean isReadyToRepeat() {
        if (isOutDated()) {
            return false;
        }

        if (period == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        LocalDate theDateOfRespawn = Timestamp.from(getCreateAt().toInstant().plus(votingTime)).toLocalDateTime().toLocalDate();

        if (now.compareTo(theDateOfRespawn) != 0) {
            return false;
        }

        return true;
    }

    private boolean isOutDated() {
        if (upToDate == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        LocalDate upToDate = this.getUpToDate().toLocalDateTime().toLocalDate();

        if (now.compareTo(upToDate) <= 0) {
            return false;
        }
        return true;
    }
    public void setCreateAt(Timestamp createAt) {
        if (createAt == null) {
            nowCreateAt();
            return;
        }
        this.createAt = createAt;
    }

    private void nowCreateAt() {
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
