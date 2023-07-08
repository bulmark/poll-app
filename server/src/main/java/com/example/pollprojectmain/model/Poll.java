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
import java.time.temporal.ChronoUnit;
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Spectator> spectators = new ArrayList<>();

    private Boolean repeated;

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
        if (getRepeated() || isOutDated() || period == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime theDateOfRespawn = Timestamp.from(getCreateAt().toInstant()
                .plus(period))
                .toLocalDateTime()
                .truncatedTo(ChronoUnit.MINUTES);

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
            setCurrentTimeToCreateAt();
            return;
        }
        this.createAt = createAt;
    }

    public void setRepeated(Boolean repeated) {
        if (repeated == null) {
            repeated = false;
            return;
        }
        this.repeated = repeated;
    }

    private void setCurrentTimeToCreateAt() {
        this.createAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public Poll produceCopy() {
        Poll copyPoll = new Poll();

        copyPoll.setOwner(this.getOwner());
        copyPoll.setText(this.getText());
        copyPoll.setPeriod(this.getPeriod());
        copyPoll.setUpToDate(this.getUpToDate());

//        for (Spectator spectator : this.getSpectators()) {
//            copyPoll.getSpectators().add(spectator.produceCopyTo(copyPoll));
//        }

        for (Question question : this.getQuestions()) {
            copyPoll.getQuestions().add(question.produceCopyTo(copyPoll));
        }

        copyPoll.setCurrentTimeToCreateAt();
        copyPoll.setRepeated(false);

        return copyPoll;
    }

    public List<Spectator> produceSpectatorCopyTo(Poll poll) {
        List<Spectator> copySpectators = new ArrayList<>();
        for (Spectator spectator : this.getSpectators()) {
            copySpectators.add(new Spectator(poll, spectator.getUser()));
        }
        return copySpectators;
    }
}
