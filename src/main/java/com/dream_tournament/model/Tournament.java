package com.dream_tournament.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String tournamentName;

    // TODO: cascade and etc. will be discussed
    @OneToMany(mappedBy = "activeTournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> participants = new HashSet<>();

    @Column(nullable = false)
    private Boolean rewardsClaimed;

    @PrePersist
    protected void onCreate() {
        this.rewardsClaimed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Boolean getRewardsClaimed() {
        return rewardsClaimed;
    }

    public void setRewardsClaimed(Boolean rewardsClaimed) {
        this.rewardsClaimed = rewardsClaimed;
    }


    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", tournamentName='" + tournamentName + '\'' +
                ", participants=" + participants +
                ", rewardsClaimed=" + rewardsClaimed +
                '}';
    }
}
