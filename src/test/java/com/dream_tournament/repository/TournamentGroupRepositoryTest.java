package com.dream_tournament.repository;

import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TournamentGroupRepositoryTest {

    @Autowired
    private TournamentGroupRepository tournamentGroupRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserRepository userRepository;

    private Tournament testTournament;
    private User testUser;
    private TournamentGroup testTournamentGroup;

    @BeforeEach
    public void setUp() {
        testTournament = new Tournament();
        tournamentRepository.save(testTournament);

        testUser = new User("Yigit Cevik");
        userRepository.save(testUser);

        testTournamentGroup = new TournamentGroup(testTournament);
        tournamentGroupRepository.save(testTournamentGroup);
    }


    @Test
    public void testFindAllByTournamentId() {
        List<TournamentGroup> groups = tournamentGroupRepository.findAllByTournamentId(testTournament.getId());

        assertThat(groups).isNotEmpty();
        assertThat(groups.size()).isEqualTo(1);
    }
}
