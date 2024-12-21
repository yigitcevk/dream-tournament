package com.dream_tournament.dto;

public record GroupLeaderboardEntry(
        Long userId,
        String username,
        String country,
        Integer score
) {}
