package com.dream_tournament.dto.response;

public class ClaimRewardResponse {
    private final Integer coins;
    private final Boolean rewardsClaimed;

    public ClaimRewardResponse(Integer coins, Boolean rewardsClaimed) {
        this.coins = coins;
        this.rewardsClaimed = rewardsClaimed;
    }

    public Integer getCoins() {
        return coins;
    }

    public Boolean getRewardsClaimed() {
        return rewardsClaimed;
    }
}
