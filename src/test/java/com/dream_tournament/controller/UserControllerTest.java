package com.dream_tournament.controller;

import com.dream_tournament.dto.request.CreateUserRequest;
import com.dream_tournament.dto.request.UpdateLevelRequest;
import com.dream_tournament.dto.response.CreateUserResponse;
import com.dream_tournament.dto.response.UpdateLevelResponse;
import com.dream_tournament.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private CreateUserResponse createUserResponse;
    private UpdateLevelResponse updateLevelResponse;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        createUserResponse = new CreateUserResponse(1L, "Turkey", 1, 5000);
        updateLevelResponse = new UpdateLevelResponse(2, 5025);
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test_user");

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(createUserResponse);

        mockMvc.perform(post("/api/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.country").value("Turkey"))
                .andExpect(jsonPath("$.level").value(1))
                .andExpect(jsonPath("$.coins").value(5000));
    }

    @Test
    void testUpdateLevel() throws Exception {
        UpdateLevelRequest request = new UpdateLevelRequest();
        request.setUserId(1L);

        when(userService.updateLevel(any(UpdateLevelRequest.class))).thenReturn(updateLevelResponse);

        mockMvc.perform(put("/api/user/level")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value(2))
                .andExpect(jsonPath("$.coins").value(5025));
    }
}