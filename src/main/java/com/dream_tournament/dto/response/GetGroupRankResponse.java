package com.dream_tournament.dto.response;

public class GetGroupRankResponse {
    private final Integer rank;

    public GetGroupRankResponse(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {
        return rank;
    }
}
