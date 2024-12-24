package com.dream_tournament.repository;

import com.dream_tournament.model.User;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User(
                "Yigit Cevik"
        );
        userRepository.save(testUser);
    }

    @Test
    public void testFindByUsername_UserExists() {
        Optional<User> user = userRepository.findByUsername("Yigit Cevik");

        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("Yigit Cevik");
    }

    @Test
    public void testFindByUsername_UserDoesNotExist() {
        Optional<User> user = userRepository.findByUsername("Yigitooo");

        assertThat(user).isNotPresent();
    }
}
