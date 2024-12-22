package com.dream_tournament.controller;

import com.dream_tournament.dto.request.*;
import com.dream_tournament.dto.response.*;
import com.dream_tournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournament")
@Validated
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping("/enter")
    public ResponseEntity<EnterTournamentResponse> enterTournament(@RequestBody @Validated EnterTournamentRequest request) {
        EnterTournamentResponse response = tournamentService.enterTournament(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/claim-reward")
    public ResponseEntity<ClaimRewardResponse> claimReward(@RequestBody @Validated ClaimRewardRequest request) {
        ClaimRewardResponse response = tournamentService.claimReward(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group-rank")
    public ResponseEntity<GetGroupRankResponse> getGroupRank(@RequestBody @Validated GetGroupRankRequest request) {
        GetGroupRankResponse response = tournamentService.getGroupRank(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group-leaderboard")
    public ResponseEntity<GetGroupLeaderboardResponse> getGroupLeaderboard(@RequestBody @Validated GetGroupLeaderboardRequest request) {
        GetGroupLeaderboardResponse response = tournamentService.getGroupLeaderboard(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country-leaderboard")
    public ResponseEntity<GetCountryLeaderboardResponse> getCountryLeaderboard(@RequestBody @Validated GetCountryLeaderboardRequest request) {
        GetCountryLeaderboardResponse response = tournamentService.getCountryLeaderboard(request);
        return ResponseEntity.ok(response);
    }

}
