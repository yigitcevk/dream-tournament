package com.dream_tournament.dto.response;

public class UpdateLevelResponse {
    private final Integer level;
    private final Integer coins;

    public UpdateLevelResponse(Integer level, Integer coins) {
        this.level = level;
        this.coins = coins;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getCoins() {
        return coins;
    }
}
