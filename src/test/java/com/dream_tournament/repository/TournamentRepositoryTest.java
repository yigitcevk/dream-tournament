package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    private Tournament activeTournament;

    @BeforeEach
    public void setUp() {
        activeTournament = tournamentRepository.findByIsActiveTrue();
        if (activeTournament == null) {
            activeTournament = new Tournament();
        }
        tournamentRepository.save(activeTournament);
    }

    @Test
    public void testFindByIsActiveTrue() {
        assertThat(activeTournament).isNotNull();
        assertThat(activeTournament.getActive()).isTrue();
    }

    @Test
    public void testFindById_TournamentExists() {
        Optional<Tournament> tournament = tournamentRepository.findById(activeTournament.getId());

        assertThat(tournament).isPresent();
    }

    @Test
    public void testFindById_TournamentDoesNotExist() {
        Optional<Tournament> tournament = tournamentRepository.findById(999);

        assertThat(tournament).isNotPresent();
    }

}
