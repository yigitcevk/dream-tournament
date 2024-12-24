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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TournamentService {

    private final int MIN_TOURNAMENT_LEVEL = 20;
    private final int MIN_TOURNAMENT_REQUIRED_COIN = 5000;
    private final int TOURNAMENT_ENTRY_FEE = 1000;
    private final int START_SCORE = 0;
    private final int FIRST_PLACE_REWARD = 10000;
    private final int SECOND_PLACE_REWARD = 5000;

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final GroupParticipantRepository groupParticipantRepository;
    private final TournamentGroupRepository tournamentGroupRepository;

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

    /**
     * Enter to the active tournament.
     *
     * @param request userId
     * @return List<GroupLeaderboardEntry>
     */
    public EnterTournamentResponse enterTournament(EnterTournamentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));

        if (user.getLevel() < MIN_TOURNAMENT_LEVEL) {
            throw new IllegalArgumentException("User must be at least level 20 to enter the tournament, Level: " + user.getLevel());
        }

        if (user.getCoins() < MIN_TOURNAMENT_REQUIRED_COIN) {
            throw new IllegalArgumentException("User does not have enough coins to enter the tournament, Coins: " + user.getCoins());
        }
        if (user.getActiveTournament()) {
            throw new IllegalArgumentException("User must claim previous rewards before entering a new tournament, Claimed: " + user.getActiveTournament());
        }

        user.setCoins(user.getCoins() - TOURNAMENT_ENTRY_FEE);
        userRepository.save(user);

        Tournament activeTournament = tournamentRepository.findByIsActiveTrue();
        if (activeTournament == null) {
            throw new IllegalStateException("No active tournament available");
        }

        TournamentGroup group = matchUserToGroup(user, activeTournament);

        List<GroupLeaderboardEntry> group_leaderboard = getGroupLeaderboardEntries(group, user);
        return new EnterTournamentResponse(group_leaderboard);
    }

    /**
     * Get all group participants in leaderboard
     *
     * @param user the user entity
     * @param group the tournament group entity
     * @return List<GroupLeaderboardEntry>
     */
    private List<GroupLeaderboardEntry> getGroupLeaderboardEntries(TournamentGroup group, User user) {
        List<GroupLeaderboardEntry> group_leaderboard = new ArrayList<>();
        for (GroupParticipant participant : group.getParticipants()) {
            group_leaderboard.add(new GroupLeaderboardEntry(
                    participant.getUser().getId(),
                    participant.getUser().getUsername(),
                    participant.getUser().getCountry(),
                    participant.getScore()
            ));
        }
        group_leaderboard.add(new GroupLeaderboardEntry(
                user.getId(),
                user.getUsername(),
                user.getCountry(),
                START_SCORE
        ));
        return group_leaderboard;
    }

    /**
     * Match user to available group or create a new one
     *
     * @param user the user entity
     * @param tournament the tournament entity
     * @return List<TournamentGroup>
     */
    private TournamentGroup matchUserToGroup(User user, Tournament tournament) {
        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId((tournament.getId()));

        for (TournamentGroup group : groups) {
            boolean sameCountry = false;
            int group_size = 0;
            for (GroupParticipant participant : group.getParticipants()) {
                group_size++;
                if (participant.getUser().getCountry().equals(user.getCountry())) {
                    sameCountry = true;
                    break;
                }
            }

            if (!sameCountry && group_size < 5) {
                GroupParticipant participant = new GroupParticipant(
                        user,
                        group
                );
                groupParticipantRepository.save(participant);
                group_size++;
                if (group_size == 5) {
                    for (GroupParticipant p : group.getParticipants()) {
                        p.getUser().setActiveTournament(true);
                        userRepository.save(p.getUser());
                    }
                    user.setActiveTournament(true);
                    userRepository.save(user);
                }
                return group;
            }

        }

        TournamentGroup newGroup = new TournamentGroup(
                tournament
        );
        tournamentGroupRepository.save(newGroup);

        GroupParticipant participant = new GroupParticipant(
                user,
                newGroup
        );
        groupParticipantRepository.save(participant);

        return newGroup;
    }

    /**
     * Claim rewards for given userId.
     *
     * @param request userId
     * @return coin, rewardsClaimed
     */
    public ClaimRewardResponse claimReward(ClaimRewardRequest request) {
        Tournament activeTournament = tournamentRepository.findByIsActiveTrue();
        if (activeTournament != null) {
            throw new IllegalStateException("Please wait for the tournament to end");
        }

        Tournament latestTournament = tournamentRepository.findByLatestTrue();
        if (latestTournament == null) {
            throw new IllegalStateException("Latest tournament not found");
        }

        GetGroupRankResponse groupRankResponse = getGroupRank(new GetGroupRankRequest(
                request.getUserId(),
                latestTournament.getId()
        ));
        int rank = groupRankResponse.getRank();

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        if (!user.getActiveTournament()) {
            throw new IllegalStateException("Reward is already claimed");
        }

        if (rank == 1) {
            user.setCoins(user.getCoins() + FIRST_PLACE_REWARD);
        } else if (rank == 2) {
            user.setCoins(user.getCoins() + SECOND_PLACE_REWARD);
        }

        user.setActiveTournament(false);
        userRepository.save(user);

        return new ClaimRewardResponse(
                user.getCoins(),
                true
        );
    }

    /**
     * Get group rank for any tournament for given userId and tournamentId
     *
     * @param request userId, tournamentId
     * @return rank
     */
    public GetGroupRankResponse getGroupRank(GetGroupRankRequest request) {
        Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found, TournamentId: " + request.getTournamentId()));

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

    /**
     * Get Group Leaderboard
     *
     * @param request groupId
     * @return List<GroupLeaderboardEntry>
     */
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

        leaderboard.sort((e1, e2) -> Integer.compare(e2.score(), e1.score()));

        return new GetGroupLeaderboardResponse(leaderboard);
    }

    /**
     * Get Country Leaderboard
     *
     * @param request tournamentId
     * @return List<CountryLeaderboardEntry>
     */
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
