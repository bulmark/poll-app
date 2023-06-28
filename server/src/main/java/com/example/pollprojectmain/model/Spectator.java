package com.example.pollprojectmain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spectators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SpectatorId.class)
public class Spectator {

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @Column(name = "can_read_results")
    private Boolean canReadResults;

    public Spectator(Poll poll, User user) {
        this.poll = poll;
        this.user = user;
    }

    public Spectator produceCopyTo(Poll poll) {
        Spectator copySpectator = new Spectator();

        User user = new User();
        user.setId(this.getUser().getId());

        copySpectator.setPoll(poll);
        copySpectator.setUser(this.getUser());
        copySpectator.setCanReadResults(this.getCanReadResults());

        return copySpectator;
    }
}
