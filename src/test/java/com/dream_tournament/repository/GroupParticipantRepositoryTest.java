package com.dream_tournament.repository;

import com.dream_tournament.model.GroupParticipant;
import com.dream_tournament.model.Tournament;
import com.dream_tournament.model.TournamentGroup;
import com.dream_tournament.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupParticipantRepositoryTest {

    @Autowired
    private GroupParticipantRepository groupParticipantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentGroupRepository tournamentGroupRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    private User user;
    private TournamentGroup group;

    @BeforeEach
    void setUp() {
        Tournament tournament = new Tournament();
        tournament.setActive(true);
        tournamentRepository.save(tournament);

        user = new User();
        user.setUsername("player1");
        userRepository.save(user);

        group = new TournamentGroup();
        group.setTournament(tournament);
        tournamentGroupRepository.save(group);

        GroupParticipant participant = new GroupParticipant();
        participant.setUser(user);
        participant.setTournamentGroup(group);
        groupParticipantRepository.save(participant);
    }

    @AfterEach
    void tearDown() {
        groupParticipantRepository.deleteAll();
        tournamentGroupRepository.deleteAll();
        userRepository.deleteAll();
        tournamentRepository.deleteAll();
    }

    @Test
    void testFindAllByTournamentGroupId() {
        List<GroupParticipant> participants = groupParticipantRepository.findAllByTournamentGroupId(group.getId());
        Assertions.assertThat(participants.size()).isEqualTo(1);
    }
}
