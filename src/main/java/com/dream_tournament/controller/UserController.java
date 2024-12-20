package com.dream_tournament.controller;

import com.dream_tournament.dto.CreateUserRequest;
import com.dream_tournament.dto.CreateUserResponse;
import com.dream_tournament.dto.UpdateLevelRequest;
import com.dream_tournament.dto.UpdateLevelResponse;
import com.dream_tournament.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/level")
    public ResponseEntity<UpdateLevelResponse> updateLevel(@RequestBody @Validated UpdateLevelRequest request) {
        UpdateLevelResponse response = userService.updateLevel(request);
        return ResponseEntity.ok(response);
    }
}
