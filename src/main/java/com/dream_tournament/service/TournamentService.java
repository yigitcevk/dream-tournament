package com.dream_tournament.service;

import com.dream_tournament.dto.CountryLeaderboardEntry;
import com.dream_tournament.dto.GroupLeaderboardEntry;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        Tournament activeTournament = tournamentRepository.findByIsActiveTrue()
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
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TournamentGroup group = groupParticipantRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User is not part of a tournament group"));

        List<GroupParticipant> participants = groupParticipantRepository.findAllByTournamentGroupId(group.getId());
        for (int i = 0; i < participants.size() - 1; i++) {
            for (int j = 0; j < participants.size() - i - 1; j++) {
                if (participants.get(j).getScore() < participants.get(j + 1).getScore()) {
                    GroupParticipant temp = participants.get(j);
                    participants.set(j, participants.get(j + 1));
                    participants.set(j + 1, temp);
                }
            }
        }

        int rank = 0;
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getUser().getId().equals(user.getId())) {
                rank = i + 1;
                break;
            }
        }

        if (rank == 1) {
            user.setCoins(user.getCoins() + 10000);
        } else if (rank == 2) {
            user.setCoins(user.getCoins() + 5000);
        }

        user.setRewardsClaimed(true);
        userRepository.save(user);

        return new ClaimRewardResponse(
                user.getCoins(),
                user.getRewardsClaimed()
        );
    }

    public GetGroupRankResponse getGroupRank(GetGroupRankRequest request) {
        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found"));

        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId(tournament.getId());

        int rank = 0;
        for (TournamentGroup group : groups) {
            for (GroupParticipant participant : group.getParticipants()) {
                if (participant.getUser().getId().equals(request.getUserId())) {
                    List<GroupParticipant> participants = group.getParticipants();

                    participants.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

                    for (int i = 0; i < participants.size(); i++) {
                        if (participants.get(i).getUser().getId().equals(request.getUserId())) {
                            rank = i + 1;
                        }
                    }
                }
            }
        }
        return new GetGroupRankResponse(rank);
    }

    public GetGroupLeaderboardResponse getGroupLeaderboard(GetGroupLeaderboardRequest request) {
        List<GroupParticipant> participants = groupParticipantRepository.findAllByTournamentGroupId(request.getGroupId());

        List<GroupLeaderboardEntry> leaderboard = new ArrayList<>();
        for (GroupParticipant participant : participants) {
            leaderboard.add(new GroupLeaderboardEntry(
                    participant.getUser().getId(),
                    participant.getUser().getUsername(),
                    participant.getUser().getCountry(),
                    participant.getScore()));
        }

        leaderboard.sort((e1, e2) -> Integer.compare(e1.score(), e2.score()));

        return new GetGroupLeaderboardResponse(leaderboard);
    }

    public GetCountryLeaderboardResponse getCountryLeaderboard(GetCountryLeaderboardRequest request) {
        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId(request.getTournamentId());
        Map<String, Integer> countryScores = new HashMap<>();

        for (TournamentGroup group : groups) {
            for (GroupParticipant participant : group.getParticipants()) {
                String country = participant.getUser().getCountry();
                int score = participant.getScore();

                countryScores.put(country, countryScores.getOrDefault(country, 0) + score);
            }
        }

        List<CountryLeaderboardEntry> leaderboard = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countryScores.entrySet()) {
            leaderboard.add(new CountryLeaderboardEntry(entry.getKey(), entry.getValue()));
        }

        leaderboard.sort((e1, e2) -> Integer.compare(e2.totalScore(), e1.totalScore()));

        return new GetCountryLeaderboardResponse(leaderboard);
    }
}
