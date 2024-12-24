package com.dream_tournament.repository;

import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Integer> {

    @Query("SELECT tg FROM TournamentGroup tg JOIN tg.participants gp WHERE gp.user.id = :userId AND tg.tournament.id = :tournamentId")
    TournamentGroup findByUserIdAndTournamentId(@Param("userId") Integer userId, @Param("tournamentId") Integer tournamentId);

    /**
     * Retrieves all tournament groups associated with the given tournament ID.
     *
     * @param tournamentId the tournament ID
     * @return a list of tournament groups
     */
    List<TournamentGroup> findAllByTournamentId(Integer tournamentId);

}
