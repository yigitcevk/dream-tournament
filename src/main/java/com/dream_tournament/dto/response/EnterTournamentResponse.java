package com.dream_tournament.dto.response;

import com.dream_tournament.dto.GroupLeaderboardEntry;

import java.util.List;

public class EnterTournamentResponse {
    private List<GroupLeaderboardEntry> leaderboard;

    public EnterTournamentResponse(List<GroupLeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
