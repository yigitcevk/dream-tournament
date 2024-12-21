package com.dream_tournament.repository;

import com.dream_tournament.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Integer> {
    List<TournamentGroup> findAllByTournamentId(Long tournament_Id);
}
