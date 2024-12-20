package com.dream_tournament.dto;

import jakarta.validation.constraints.NotBlank;


public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
