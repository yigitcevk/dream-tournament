package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetGroupRankRequest {
    @NotNull(message = "User ID is required")
    private final Integer userId;

    @NotNull(message = "Tournament ID is required")
    private final Integer tournamentId;

    public GetGroupRankRequest(Integer userId, Integer tournamentId) {
        this.userId = userId;
        this.tournamentId = tournamentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }
}
