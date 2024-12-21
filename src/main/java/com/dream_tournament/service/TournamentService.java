package com.dream_tournament.service;

import com.dream_tournament.dto.GroupLeaderboardEntry;
import com.dream_tournament.dto.request.ClaimRewardRequest;
import com.dream_tournament.dto.request.EnterTournamentRequest;
import com.dream_tournament.dto.request.GetCountryLeaderboardRequest;
import com.dream_tournament.dto.request.GetGroupLeaderboardRequest;
import com.dream_tournament.dto.response.*;
import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.GroupParticipantRepository;
import com.dream_tournament.repository.TournamentGroupRepository;
import com.dream_tournament.repository.TournamentRepository;
import com.dream_tournament.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final GroupParticipantRepository groupParticipantRepository;
    private final TournamentGroupRepository tournamentGroupRepository;

    @Autowired
    public TournamentService(
            TournamentRepository tournamentRepository,
            UserRepository userRepository,
            GroupParticipantRepository groupParticipantRepository,
            TournamentGroupRepository tournamentGroupRepository
    ) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
        this.groupParticipantRepository = groupParticipantRepository;
        this.tournamentGroupRepository = tournamentGroupRepository;
    }


    public EnterTournamentResponse enterTournament(EnterTournamentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getLevel() < 20) {
            throw new IllegalArgumentException("User must be at least level 20 to enter the tournament");
        }

        if (user.getCoins() < 1000) {
            throw new IllegalArgumentException("User does not have enough coins to enter the tournament");
        }

        if (!user.getRewardsClaimed()) {
            throw new IllegalArgumentException("User must claim previous rewards before entering a new tournament");
        }

        user.setCoins(user.getCoins() - 1000);
        userRepository.save(user);

        Tournament activeTournament = tournamentRepository.findByIsActiveTrue(Boolean.TRUE)
                .orElseThrow(() -> new IllegalStateException("No active tournament available"));

        TournamentGroup group = matchUserToGroup(user, activeTournament);

        List<GroupLeaderboardEntry> group_leaderboard = new ArrayList<>();
        for (GroupParticipant participant : group.getParticipants()) {
            group_leaderboard.add(new GroupLeaderboardEntry(
                    participant.getUser().getId(),
                    participant.getUser().getUsername(),
                    participant.getUser().getCountry(),
                    participant.getScore()
            ));
        }
        return new EnterTournamentResponse(group_leaderboard);
    }

    private TournamentGroup matchUserToGroup(User user, Tournament tournament) {
        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId((tournament.getId()));

        for (TournamentGroup group : groups) {
            boolean sameCountry = false;
            for (GroupParticipant participant : group.getParticipants()) {
                if (participant.getUser().getCountry().equals(user.getCountry())) {
                    sameCountry = true;
                    break;
                }
            }

            if (!sameCountry) {
                GroupParticipant participant = new GroupParticipant();
                participant.setUser(user);
                participant.setTournamentGroup(group);
                groupParticipantRepository.save(participant);
                return group;
            }
        }

        TournamentGroup newGroup = new TournamentGroup();
        newGroup.setTournament(tournament);
        tournamentGroupRepository.save(newGroup);

        GroupParticipant participant = new GroupParticipant();
        participant.setUser(user);
        participant.setTournamentGroup(newGroup);
        groupParticipantRepository.save(participant);

        return newGroup;
    }

    public ClaimRewardResponse claimReward(ClaimRewardRequest request) {
    }

    public GetGroupRankResponse getGroupRank(GetGroupLeaderboardRequest request) {
    }

    public GetGroupLeaderboardResponse getGroupLeaderboard(GetGroupLeaderboardRequest request) {
    }

    public GetCountryLeaderboardResponse getCountryLeaderboard(GetCountryLeaderboardRequest request) {
    }
}
