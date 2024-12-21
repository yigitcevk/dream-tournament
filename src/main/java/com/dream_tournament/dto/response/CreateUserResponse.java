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

}
