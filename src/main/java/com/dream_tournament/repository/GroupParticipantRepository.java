package com.dream_tournament.repository;

import com.dream_tournament.model.GroupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupParticipantRepository extends JpaRepository<GroupParticipant, Integer> {
}
