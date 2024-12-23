package com.dream_tournament.dto;

public record GroupLeaderboardEntry(
        Integer userId,
        String username,
        String country,
        Integer score
) {}
