package com.dream_tournament.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, updatable = false)
    private String country;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer coins;

    // TODO: will be discussed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_tournament_id")
    private Tournament activeTournament;

    @PrePersist
    protected void onCreate() {
        this.level = 1;
        this.coins = 5000;
        this.country = assignRandomCountry();
    }

    private String assignRandomCountry() {
        List<String> countries = List.of("Turkey", "United States", "United Kingdom", "France", "Germany");
        Random random = new Random();
        return countries.get(random.nextInt(countries.size()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Tournament getActiveTournament() {
        return activeTournament;
    }

    public void setActiveTournament(Tournament activeTournament) {
        this.activeTournament = activeTournament;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", country='" + country + '\'' +
                ", level=" + level +
                ", coins=" + coins +
                ", activeTournament=" + activeTournament +
                '}';
    }
}
