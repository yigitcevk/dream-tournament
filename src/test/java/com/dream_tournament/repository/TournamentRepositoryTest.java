package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setUp() {
        Tournament activeTournament = new Tournament();
        activeTournament.setActive(true);
        tournamentRepository.save(activeTournament);

        Tournament inactiveTournament = new Tournament();
        inactiveTournament.setActive(false);
        tournamentRepository.save(inactiveTournament);
    }

    @AfterEach
    void tearDown() {
        tournamentRepository.deleteAll();
    }

    @Test
    void testSaveTournament() {
        Tournament newTournament = new Tournament();
        newTournament.setActive(true);
        Tournament savedTournament = tournamentRepository.save(newTournament);

        Assertions.assertThat(savedTournament).isNotNull();
        Assertions.assertThat(savedTournament.getActive()).isTrue();
    }

    @Test
    void testDeleteTournament() {
        Tournament tournament = new Tournament();
        tournament.setActive(false);
        Tournament savedTournament = tournamentRepository.save(tournament);
        tournamentRepository.delete(savedTournament);

        Optional<Tournament> deletedTournament = tournamentRepository.findById(savedTournament.getId());
        Assertions.assertThat(deletedTournament).isEmpty();
    }
}
