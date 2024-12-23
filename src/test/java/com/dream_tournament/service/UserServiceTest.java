package com.dream_tournament.service;

import com.dream_tournament.dto.request.CreateUserRequest;
import com.dream_tournament.dto.response.CreateUserResponse;
import com.dream_tournament.dto.request.UpdateLevelRequest;
import com.dream_tournament.dto.response.UpdateLevelResponse;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateUser_Success() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test_user");

        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        CreateUserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void testCreateUser_UsernameExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test_user");

        User existingUser = new User();
        existingUser.setUsername("test_user");

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(existingUser));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(request));

        assertEquals("Username already exists: test_user", exception.getMessage());
    }

    @Test
    void testUpdateLevel_Success() {
        User user = new User();
        user.setId(1L);
        user.setLevel(10);
        user.setCoins(100);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UpdateLevelRequest request = new UpdateLevelRequest();
        request.setUserId(1L);

        UpdateLevelResponse response = userService.updateLevel(request);

        assertNotNull(response);
        assertEquals(11, response.getLevel());
        assertEquals(125, response.getCoins());
    }

    @Test
    void testUpdateLevel_UserNotFound() {
        UpdateLevelRequest request = new UpdateLevelRequest();
        request.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userService.updateLevel(request));

        assertEquals("User not found for ID: 1", exception.getMessage());
    }
}
