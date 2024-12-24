package com.dream_tournament.service;

import com.dream_tournament.dto.request.EnterTournamentRequest;
import com.dream_tournament.dto.response.EnterTournamentResponse;
import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupParticipantRepository groupParticipantRepository;

    @Mock
    private TournamentGroupRepository tournamentGroupRepository;

    @InjectMocks
    private TournamentService tournamentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnterTournament_Success() {
        User user = new User("testuser");
        user.setId(1);
        user.setLevel(20);
        user.setCoins(2000);
        user.setActiveTournament(false);

        Tournament activeTournament = new Tournament();


        TournamentGroup group = new TournamentGroup();


        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(tournamentRepository.findByIsActiveTrue()).thenReturn(activeTournament);
        when(tournamentGroupRepository.findAllByTournamentId(1)).thenReturn(new ArrayList<>());
        when(tournamentGroupRepository.save(any(TournamentGroup.class))).thenReturn(group);

        EnterTournamentRequest request = new EnterTournamentRequest(1);
        EnterTournamentResponse response = tournamentService.enterTournament(request);

        assertThat(response).isNotNull();

        verify(userRepository, times(1)).save(any(User.class));
        verify(groupParticipantRepository, times(1)).save(any());
    }

    @Test
    public void testEnterTournament_UserNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        EnterTournamentRequest request = new EnterTournamentRequest(99);
        assertThrows(IllegalArgumentException.class, () -> tournamentService.enterTournament(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testEnterTournament_InsufficientLevel() {
        User user = new User("lowleveluser");
        user.setId(1);
        user.setLevel(10);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        EnterTournamentRequest request = new EnterTournamentRequest(1);
        assertThrows(IllegalArgumentException.class, () -> tournamentService.enterTournament(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testEnterTournament_InsufficientCoins() {
        User user = new User("nocoinuser");
        user.setId(1);
        user.setLevel(20);
        user.setCoins(500);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        EnterTournamentRequest request = new EnterTournamentRequest(1);
        assertThrows(IllegalArgumentException.class, () -> tournamentService.enterTournament(request));

        verify(userRepository, never()).save(any(User.class));
    }
}