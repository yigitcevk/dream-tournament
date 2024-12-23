package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    /**
     * Retrieves only active tournament if exists
     * isActive column with true value
     *
     * @return an optional containing the tournament if found
     */
    Optional<Tournament> findByIsActiveTrue();

    /**
     * Retrieves the tournament associated with the given tournament ID.
     *
     * @param tournamentId the tournament ID
     * @return an optional containing the tournament if found
     */
    Optional<Tournament> findById(Integer tournamentId);
}
