package com.dream_tournament.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, updatable = false)
    private String country;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer coins;

    @Column(nullable = false)
    private Boolean rewardsClaimed;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    @PrePersist
    protected void onCreate() {
        this.level = 1;
        this.coins = 5000;
        this.country = assignRandomCountry();
        this.rewardsClaimed = true;
    }

    private String assignRandomCountry() {
        List<String> countries = List.of("Turkey", "United States", "United Kingdom", "France", "Germany");
        Random random = new Random();
        return countries.get(random.nextInt(countries.size()));
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCountry() {
        return country;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getCoins() {
        return coins;
    }

    public Boolean getRewardsClaimed() {
        return rewardsClaimed;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public void setRewardsClaimed(Boolean rewardsClaimed) {
        this.rewardsClaimed = rewardsClaimed;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", country='" + country + '\'' +
                ", level=" + level +
                ", coins=" + coins +
                ", rewardsClaimed=" + rewardsClaimed +
                '}';
    }
}
