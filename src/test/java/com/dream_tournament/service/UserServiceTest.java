package com.dream_tournament.service;

import com.dream_tournament.dto.request.CreateUserRequest;
import com.dream_tournament.dto.request.UpdateLevelRequest;
import com.dream_tournament.dto.response.CreateUserResponse;
import com.dream_tournament.dto.response.UpdateLevelResponse;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupParticipantRepository groupParticipantRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TournamentGroupRepository tournamentGroupRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        CreateUserRequest request = new CreateUserRequest("Yigit Cevik");

        User savedUser = new User("Yigit Cevik");
        savedUser.setLevel(1);
        savedUser.setCoins(100);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        CreateUserResponse response = userService.createUser(request);

        assertThat(response).isNotNull();
        assertThat(response.getLevel()).isEqualTo(1);
        assertThat(response.getCoins()).isEqualTo(100);

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testCreateUser_UsernameExists() {
        CreateUserRequest request = new CreateUserRequest("Yigit Cevik");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new User("Yigit Cevik")));

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(request));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateLevel_Success() {
        UpdateLevelRequest request = new UpdateLevelRequest(1);
        User user = new User("Yigit Cevik");
        user.setLevel(1);
        user.setCoins(100);
        user.setActiveTournament(false);

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateLevelResponse response = userService.updateLevel(request);

        assertThat(response).isNotNull();
        assertThat(response.getLevel()).isEqualTo(2); // Check if level incremented
        assertThat(response.getCoins()).isEqualTo(125); // Check if coins incremented

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateLevel_UserNotFound() {
        UpdateLevelRequest request = new UpdateLevelRequest(99);

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateLevel(request));

        verify(userRepository, never()).save(any(User.class));
    }
}