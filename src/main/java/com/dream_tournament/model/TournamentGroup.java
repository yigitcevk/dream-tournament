package com.dream_tournament.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "tournament_group")
public class TournamentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupParticipant> participants = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<GroupParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<GroupParticipant> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "TournamentGroup{" +
                "id=" + id +
                ", tournament=" + tournament +
                ", participants=" + participants +
                '}';
    }
}
