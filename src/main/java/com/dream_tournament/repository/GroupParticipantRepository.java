package com.dream_tournament.repository;

import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Integer> {

    /**
     * Finds the tournament group associated with the given user ID.
     *
     * @param userId the user ID
     * @return an optional containing the tournament group if found
     */
    Optional<TournamentGroup> findByUserId(Integer userId);

    /**
     * Retrieves all group participants associated with the given tournament group ID.
     *
     * @param tournamentGroupId the tournament group ID
     * @return a list of group participants
     */
    List<GroupParticipant> findAllByTournamentGroupId(Integer tournamentGroupId);
}
