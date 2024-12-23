package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetCountryLeaderboardRequest {
    @NotNull(message = "Tournament ID is required")
    private final Integer tournamentId;

    public GetCountryLeaderboardRequest(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

}
