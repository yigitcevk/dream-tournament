package com.dream_tournament.repository;

import com.dream_tournament.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves the User associated with the given unique username.
     *
     * @param username the tournament ID
     * @return an optional containing User if found
     */
    Optional<User> findByUsername(String username);
}
