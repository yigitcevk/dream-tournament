package com.dream_tournament.repository;

import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Integer> {
    Optional<TournamentGroup> findByUserId(Long userId);

    List<GroupParticipant> findAllByTournamentGroupId(Long id);
}
