package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TournamentGroupRepositoryTest {

    @Autowired
    private TournamentGroupRepository tournamentGroupRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    private Tournament tournament;

    @BeforeEach
    void setUp() {
        tournament = new Tournament();
        tournament.setActive(true);
        tournamentRepository.save(tournament);

        TournamentGroup group = new TournamentGroup();
        group.setTournament(tournament);
        tournamentGroupRepository.save(group);
    }

    @AfterEach
    void tearDown() {
        tournamentGroupRepository.deleteAll();
        tournamentRepository.deleteAll();
    }

    @Test
    void testFindAllByTournamentId() {
        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId(tournament.getId());
        Assertions.assertThat(groups.size()).isEqualTo(1);
    }
}
