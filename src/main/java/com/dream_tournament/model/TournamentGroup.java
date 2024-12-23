package com.dream_tournament.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "tournament_group")
public class TournamentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupParticipant> participants = new ArrayList<>();

    public TournamentGroup() {
    }

    public TournamentGroup(Integer id, Tournament tournament, List<GroupParticipant> participants) {
        this.id = id;
        this.tournament = tournament;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public List<GroupParticipant> getParticipants() {
        return participants;
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
