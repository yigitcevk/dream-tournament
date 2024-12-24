package com.dream_tournament.controller;

import com.dream_tournament.dto.GroupLeaderboardEntry;
import com.dream_tournament.dto.request.*;
import com.dream_tournament.dto.response.*;
import com.dream_tournament.service.TournamentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TournamentController.class)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testEnterTournament() throws Exception {
        EnterTournamentRequest request = new EnterTournamentRequest(1);
        EnterTournamentResponse response = new EnterTournamentResponse(new ArrayList<>());

        Mockito.when(tournamentService.enterTournament(Mockito.any(EnterTournamentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tournament/enter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testClaimReward() throws Exception {
        ClaimRewardRequest request = new ClaimRewardRequest(1);
        ClaimRewardResponse response = new ClaimRewardResponse(5000, true);

        Mockito.when(tournamentService.claimReward(Mockito.any(ClaimRewardRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/tournament/claim-reward")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testGetGroupLeaderboard() throws Exception {
        GetGroupLeaderboardResponse response = new GetGroupLeaderboardResponse(new ArrayList<>());

        Mockito.when(tournamentService.getGroupLeaderboard(Mockito.any(GetGroupLeaderboardRequest.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/tournament/group-leaderboard")
                        .param("groupId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testGetGroupRank() throws Exception {
        GetGroupRankResponse response = new GetGroupRankResponse(5);

        Mockito.when(tournamentService.getGroupRank(Mockito.any(GetGroupRankRequest.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/tournament/group-rank")
                        .param("tournamentId", "1")
                        .param("userId", "100"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testGetCountryLeaderboard() throws Exception {
        GetCountryLeaderboardResponse response = new GetCountryLeaderboardResponse(new ArrayList<>());

        Mockito.when(tournamentService.getCountryLeaderboard(Mockito.any(GetCountryLeaderboardRequest.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/tournament/country-leaderboard")
                        .param("tournamentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
