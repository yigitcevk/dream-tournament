package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class ClaimRewardRequest {
    @NotNull(message = "User ID is required")
    private final Integer userId;

    public ClaimRewardRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
