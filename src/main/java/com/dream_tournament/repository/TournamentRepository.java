package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    Optional<Tournament> findByIsActiveTrue();

    Optional<Tournament> findById(Long tournamentId);
}
