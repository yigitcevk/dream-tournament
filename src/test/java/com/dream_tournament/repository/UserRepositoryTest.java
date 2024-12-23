package com.dream_tournament.repository;

import com.dream_tournament.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.dream_tournament")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setUsername("player1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("player2");
        userRepository.save(user2);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindAllUsers() {
        var users = userRepository.findAll();
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void testFindByUsername() {
        Optional<Object> user = userRepository.findByUsername("player1");
        Assertions.assertThat(user).isPresent();
    }

    @Test
    void testSaveUser() {
        User newUser = new User();
        newUser.setUsername("player3");
        User savedUser = userRepository.save(newUser);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo("player3");
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUsername("player4");
        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        Assertions.assertThat(deletedUser).isEmpty();
    }
}
