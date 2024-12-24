package com.dream_tournament.repository;

import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.User;
import com.dream_tournament.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Integer> {

    /**
     * Finds a GroupParticipant by the associated User with active tournament.
     *
     * @param user the user entity
     * @param tournamentGroup the tournament group entity
     * @return the group participant object if found
     */
    GroupParticipant findByUserAndTournamentGroup(User user, TournamentGroup tournamentGroup);

    /**
     * Finds the tournament group associated with the given user ID.
     *
     * @param userId the user ID
     * @return an optional containing the tournament group if found
     */
    TournamentGroup findByUserId(Integer userId);

    /**
     * Retrieves all group participants associated with the given tournament group ID.
     *
     * @param tournamentGroupId the tournament group ID
     * @return a list of group participants
     */
    List<GroupParticipant> findAllByTournamentGroupId(Integer tournamentGroupId);
}
