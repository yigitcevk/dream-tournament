package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class EnterTournamentRequest {
    @NotNull(message = "User ID is required")
    private final Integer userId;

    public EnterTournamentRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

}
