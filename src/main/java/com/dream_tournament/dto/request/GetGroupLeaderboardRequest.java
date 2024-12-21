package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotNull;

public class GetGroupLeaderboardRequest {
    @NotNull(message = "Group ID is required")
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }
}
