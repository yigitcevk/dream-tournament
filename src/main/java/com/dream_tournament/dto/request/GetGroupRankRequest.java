package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetGroupRankRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Tournament ID is required")
    private Long tournamentId;

    public Long getUserId() {
        return userId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }
}
