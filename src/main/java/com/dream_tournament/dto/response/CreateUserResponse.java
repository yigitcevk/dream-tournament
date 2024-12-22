package com.dream_tournament.dto.response;

public class CreateUserResponse {
    private Long id;
    private String country;
    private Integer level;
    private Integer coins;

    public CreateUserResponse(Long id, String country, Integer level, Integer coins) {
        this.id = id;
        this.country = country;
        this.level = level;
        this.coins = coins;
    }

    public Long getId() {
        return id;
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
}
