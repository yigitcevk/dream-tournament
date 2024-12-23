package com.dream_tournament.repository;

import com.dream_tournament.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Integer> {

    /**
     * Retrieves all tournament groups associated with the given tournament ID.
     *
     * @param tournamentId the tournament ID
     * @return a list of tournament groups
     */
    List<TournamentGroup> findAllByTournamentId(Integer tournamentId);
}
