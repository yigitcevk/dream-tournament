package com.dream_tournament.service;

import com.dream_tournament.dto.request.CreateUserRequest;
import com.dream_tournament.dto.response.CreateUserResponse;
import com.dream_tournament.dto.request.UpdateLevelRequest;
import com.dream_tournament.dto.response.UpdateLevelResponse;
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


@Service
@Transactional
public class UserService {

    public static final int LEVEL_UP_PRIZE = 25;
    public static final int LEVEL_UP = 1;
    public static final int SCORE_UP = 1;

    private final UserRepository userRepository;
    private final GroupParticipantRepository groupParticipantRepository;
    private final TournamentRepository tournamentRepository;
    private final TournamentGroupRepository tournamentGroupRepository;


    public UserService(
            UserRepository userRepository,
            GroupParticipantRepository groupParticipantRepository,
            TournamentRepository tournamentRepository,
            TournamentGroupRepository tournamentGroupRepository
    ) {
        this.userRepository = userRepository;
        this.groupParticipantRepository = groupParticipantRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentGroupRepository = tournamentGroupRepository;
    }

    /**
     * Creates a new user.
     *
     * @param request the create user request: username
     * @return the response containing user details: id, country, level, coins
     */
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        User user = new User(
                request.getUsername()
        );
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getCountry(),
                savedUser.getLevel(),
                savedUser.getCoins()
        );
    }

    /**
     * Updates the user's level and coins.
     *
     * @param request the update level request: userId
     * @return the response containing updated level and coins: level, coins
     */
    public UpdateLevelResponse updateLevel(UpdateLevelRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + request.getUserId()));

        user.setLevel(user.getLevel() + LEVEL_UP);
        user.setCoins(user.getCoins() + LEVEL_UP_PRIZE);
        User updatedUser = userRepository.save(user);

        if (user.getActiveTournament()) {
            Tournament activeTournament = tournamentRepository.findByIsActiveTrue();
            TournamentGroup tournamentGroup = tournamentGroupRepository.findByUserIdAndTournamentId(updatedUser.getId(), activeTournament.getId());
            GroupParticipant groupParticipant = groupParticipantRepository.findByUserAndTournamentGroup(user, tournamentGroup);

            groupParticipant.setScore(groupParticipant.getScore() + SCORE_UP);
            groupParticipantRepository.save(groupParticipant);
        }

        return new UpdateLevelResponse(
                updatedUser.getLevel(),
                updatedUser.getCoins()
        );
    }
}
