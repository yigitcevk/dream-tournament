package com.dream_tournament.model;

import jakarta.persistence.*;

@Entity
@Table(name = "group_participant")
public class GroupParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TournamentGroup tournamentGroup;

    @Column(nullable = false)
    private int score;

    public GroupParticipant() {
    }

    public GroupParticipant(Integer id, User user, TournamentGroup tournamentGroup, int score) {
        this.id = id;
        this.user = user;
        this.tournamentGroup = tournamentGroup;
        this.score = score;
    }

    @PrePersist
    protected void onCreate() {
        this.score = 0;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public TournamentGroup getTournamentGroup() {
        return tournamentGroup;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "GroupParticipant{" +
                "id=" + id +
                ", user=" + user +
                ", tournamentGroup=" + tournamentGroup +
                ", score=" + score +
                '}';
    }

}
