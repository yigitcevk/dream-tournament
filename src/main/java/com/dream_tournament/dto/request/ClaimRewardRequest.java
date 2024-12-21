package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class ClaimRewardRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

}
