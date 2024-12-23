package com.dream_tournament.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tournamentName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        this.startDate = LocalDate.now();
        this.tournamentName = "Tournament-" + this.startDate;
        this.isActive = true;
    }

    public Tournament() {
    }

    public Tournament(Integer id, String tournamentName, LocalDate startDate, Boolean isActive) {
        this.id = id;
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public Boolean getActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", tournamentName='" + tournamentName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
