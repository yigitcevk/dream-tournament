package com.dream_tournament.controller;

import com.dream_tournament.dto.request.CreateUserRequest;
import com.dream_tournament.dto.request.UpdateLevelRequest;
import com.dream_tournament.dto.response.CreateUserResponse;
import com.dream_tournament.dto.response.UpdateLevelResponse;
import com.dream_tournament.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Yigit Cevik");
        CreateUserResponse response = new CreateUserResponse(1, "Turkey", 1, 5000);

        when(userService.createUser(Mockito.any(CreateUserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testUpdateLevel() throws Exception {
        UpdateLevelRequest request = new UpdateLevelRequest(1);
        UpdateLevelResponse response = new UpdateLevelResponse(2, 5025);

        when(userService.updateLevel(Mockito.any(UpdateLevelRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/user/level")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
