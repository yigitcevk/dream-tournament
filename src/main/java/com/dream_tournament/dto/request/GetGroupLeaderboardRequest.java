package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetGroupLeaderboardRequest {
    @NotNull(message = "Group ID is required")
    private final Integer groupId;

    public GetGroupLeaderboardRequest(Integer tournamentId) {
        this.groupId = tournamentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

}
