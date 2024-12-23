package com.dream_tournament.controller;

import com.dream_tournament.dto.request.EnterTournamentRequest;
import com.dream_tournament.dto.response.EnterTournamentResponse;
import com.dream_tournament.service.TournamentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TournamentController.class)
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TournamentService tournamentService;

    private EnterTournamentResponse enterTournamentResponse;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        enterTournamentResponse = new EnterTournamentResponse(null); // Add mock leaderboard data if needed
    }

    @Test
    void testEnterTournament() throws Exception {
        EnterTournamentRequest request = new EnterTournamentRequest();
        request.setUserId(1L);

        when(tournamentService.enterTournament(any(EnterTournamentRequest.class))).thenReturn(enterTournamentResponse);

        mockMvc.perform(post("/api/tournament/enter")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
