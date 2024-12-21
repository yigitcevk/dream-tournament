package com.dream_tournament.repository;

import com.dream_tournament.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
