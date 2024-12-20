DROP DATABASE IF EXISTS dream_tournament;

CREATE DATABASE IF NOT EXISTS dream_tournament;

USE dream_tournament;

CREATE TABLE IF NOT EXISTS tournaments (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           rewards_claimed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(50) NOT NULL,
    level INT DEFAULT 1,
    coins INT DEFAULT 5000,
    active_tournament_id BIGINT,
    CONSTRAINT fk_active_tournament FOREIGN KEY (active_tournament_id) REFERENCES tournaments(id) ON DELETE SET NULL
    );

INSERT INTO users (id, username, country, level, coins, active_tournament_id)
VALUES
    (1,"testUser1","Turkey", 1, 5000, NULL),
    (2,"testUser2","United States", 1, 5000, NULL),
    (3,"testUser3","United Kingdom", 1, 5000, NULL),
    (4,"testUser4","France", 1, 5000, NULL),
    (5,"testUser5","Germany", 1, 5000, NULL);
