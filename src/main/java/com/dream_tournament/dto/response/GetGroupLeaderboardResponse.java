package com.dream_tournament.dto.response;

import com.dream_tournament.dto.GroupLeaderboardEntry;

import java.util.List;

public class GetGroupLeaderboardResponse {
    private final List<GroupLeaderboardEntry> leaderboard;

    public GetGroupLeaderboardResponse(List<GroupLeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<GroupLeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }
}
