package com.dream_tournament.dto.response;

public class CreateUserResponse {
    private final Integer id;
    private final String country;
    private final Integer level;
    private final Integer coins;

    public CreateUserResponse(Integer id, String country, Integer level, Integer coins) {
        this.id = id;
        this.country = country;
        this.level = level;
        this.coins = coins;
    }

    public Integer getId() {
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
