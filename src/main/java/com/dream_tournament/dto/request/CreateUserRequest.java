package com.dream_tournament.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    private final String username;

    public CreateUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
