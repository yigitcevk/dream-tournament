package com.dream_tournament.dto.response;

import com.dream_tournament.dto.CountryLeaderboardEntry;

import java.util.List;

public class GetCountryLeaderboardResponse {
    private final List<CountryLeaderboardEntry> leaderboard;

    public GetCountryLeaderboardResponse(List<CountryLeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<CountryLeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}
