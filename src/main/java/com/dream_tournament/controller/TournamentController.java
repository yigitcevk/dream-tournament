package com.dream_tournament.controller;

import com.dream_tournament.dto.request.*;
import com.dream_tournament.dto.response.*;
import com.dream_tournament.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournament")
@Validated
public class TournamentController {

    private final TournamentService tournamentService;

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

    @GetMapping("/group-leaderboard")
    public ResponseEntity<GetGroupLeaderboardResponse> getGroupLeaderboard(
            @RequestParam("tournamentId") Integer tournamentId) {
        GetGroupLeaderboardResponse response = tournamentService.getGroupLeaderboard(new GetGroupLeaderboardRequest(tournamentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group-rank")
    public ResponseEntity<GetGroupRankResponse> getGroupRank(
            @RequestParam("tournamentId") Integer tournamentId,
            @RequestParam("userId") Integer userId) {
        GetGroupRankResponse response = tournamentService.getGroupRank(new GetGroupRankRequest(userId, tournamentId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country-leaderboard")
    public ResponseEntity<GetCountryLeaderboardResponse> getCountryLeaderboard(
            @RequestParam("tournamentId") Integer tournamentId) {
        GetCountryLeaderboardResponse response = tournamentService.getCountryLeaderboard(new GetCountryLeaderboardRequest(tournamentId));
        return ResponseEntity.ok(response);
    }

}
