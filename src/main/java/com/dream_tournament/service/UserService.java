package com.dream_tournament.service;

import com.dream_tournament.dto.CreateUserRequest;
import com.dream_tournament.dto.CreateUserResponse;
import com.dream_tournament.dto.UpdateLevelRequest;
import com.dream_tournament.dto.UpdateLevelResponse;
import com.dream_tournament.model.User;
import com.dream_tournament.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());

        User savedUser = userRepository.save(user);

        CreateUserResponse response = new CreateUserResponse();
        response.setId(savedUser.getId());
        response.setCountry(savedUser.getCountry());
        response.setLevel(savedUser.getLevel());
        response.setCoins(savedUser.getCoins());
        return response;
    }

    public UpdateLevelResponse updateLevel(UpdateLevelRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setLevel(user.getLevel() + 1);
        user.setCoins(user.getCoins() + 25);
        User updatedUser = userRepository.save(user);

        UpdateLevelResponse response = new UpdateLevelResponse();
        response.setLevel(updatedUser.getLevel());
        response.setCoins(updatedUser.getCoins());
        return response;
    }
}
