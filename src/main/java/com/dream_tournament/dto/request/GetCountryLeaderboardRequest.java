package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetCountryLeaderboardRequest {
    @NotNull(message = "Tournament ID is required")
    private Long tournamentId;

    public Long getTournamentId() {
        return tournamentId;
    }
}
