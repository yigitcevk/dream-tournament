package com.dream_tournament.service;

import com.dream_tournament.dto.request.*;
import com.dream_tournament.dto.response.*;
import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.GroupParticipantRepository;
import com.dream_tournament.repository.TournamentGroupRepository;
import com.dream_tournament.repository.TournamentRepository;
import com.dream_tournament.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    void setUp() {
    }

    @Test
    void testClaimReward_Success() {
        User user = new User();
        user.setId(1L);
        user.setCoins(500);
        user.setRewardsClaimed(false);

        TournamentGroup group = new TournamentGroup();
        group.setId(1L);

        GroupParticipant participant1 = new GroupParticipant();
        participant1.setUser(user);
        participant1.setScore(100);

        GroupParticipant participant2 = new GroupParticipant();
        participant2.setUser(new User());
        participant2.getUser().setId(2L);
        participant2.setScore(50);

        List<GroupParticipant> participants = Arrays.asList(participant1, participant2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(groupParticipantRepository.findByUserId(1L)).thenReturn(Optional.of(group));
        when(groupParticipantRepository.findAllByTournamentGroupId(1L)).thenReturn(participants);

        ClaimRewardRequest request = new ClaimRewardRequest();
        request.setUserId(1L);

        ClaimRewardResponse response = tournamentService.claimReward(request);

        assertNotNull(response);
        assertEquals(10500, response.getCoins());
        assertTrue(response.getRewardsClaimed());
    }

    @Test
    void testClaimReward_UserNotFound() {
        ClaimRewardRequest request = new ClaimRewardRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> tournamentService.claimReward(request));

        assertEquals("User not found: 1", exception.getMessage());
    }

    @Test
    void testGetGroupRank_Success() {
        User user = new User();
        user.setId(1L);

        Tournament tournament = new Tournament();
        tournament.setId(1L);

        TournamentGroup group = new TournamentGroup();
        group.setId(1L);

        GroupParticipant participant1 = new GroupParticipant();
        participant1.setUser(user);
        participant1.setScore(100);

        GroupParticipant participant2 = new GroupParticipant();
        participant2.setUser(new User());
        participant2.getUser().setId(2L);
        participant2.setScore(200);

        group.setParticipants(Arrays.asList(participant1, participant2));

        List<TournamentGroup> groups = Collections.singletonList(group);

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        when(tournamentGroupRepository.findAllByTournamentId(1L)).thenReturn(groups);

        GetGroupRankRequest request = new GetGroupRankRequest();
        request.setUserId(1L);
        request.setTournamentId(1L);

        GetGroupRankResponse response = tournamentService.getGroupRank(request);

        assertNotNull(response);
        assertEquals(2, response.getRank());
    }

    @Test
    void testGetGroupLeaderboard_Success() {
        GroupParticipant participant1 = new GroupParticipant();
        participant1.setUser(new User());
        participant1.getUser().setId(1L);
        participant1.getUser().setUsername("Player1");
        participant1.getUser().setCountry("US");
        participant1.setScore(150);

        GroupParticipant participant2 = new GroupParticipant();
        participant2.setUser(new User());
        participant2.getUser().setId(2L);
        participant2.getUser().setUsername("Player2");
        participant2.getUser().setCountry("UK");
        participant2.setScore(100);

        when(groupParticipantRepository.findAllByTournamentGroupId(1L)).thenReturn(Arrays.asList(participant1, participant2));

        GetGroupLeaderboardRequest request = new GetGroupLeaderboardRequest();
        request.setGroupId(1L);

        GetGroupLeaderboardResponse response = tournamentService.getGroupLeaderboard(request);

        assertNotNull(response);
        assertEquals(2, response.getLeaderboard().size());
        assertEquals(1, response.getLeaderboard().get(0).userId());
        assertEquals(2, response.getLeaderboard().get(1).userId());
    }

    @Test
    void testGetCountryLeaderboard_Success() {
        Tournament tournament = new Tournament();
        tournament.setId(1L);

        TournamentGroup group = new TournamentGroup();
        group.setId(1L);

        GroupParticipant participant1 = new GroupParticipant();
        participant1.setUser(new User());
        participant1.getUser().setCountry("US");
        participant1.setScore(150);

        GroupParticipant participant2 = new GroupParticipant();
        participant2.setUser(new User());
        participant2.getUser().setCountry("UK");
        participant2.setScore(100);

        group.setParticipants(Arrays.asList(participant1, participant2));

        when(tournamentGroupRepository.findAllByTournamentId(1L)).thenReturn(Collections.singletonList(group));

        GetCountryLeaderboardRequest request = new GetCountryLeaderboardRequest();
        request.setTournamentId(1L);

        GetCountryLeaderboardResponse response = tournamentService.getCountryLeaderboard(request);

        assertNotNull(response);
        assertEquals(2, response.getLeaderboard().size());
    }
}
