package com.example.pollprojectmain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spectators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SpectatorId.class)
public class Spectator {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
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
}
