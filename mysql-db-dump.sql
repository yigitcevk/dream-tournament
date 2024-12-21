-- dump.sql
DROP DATABASE IF EXISTS dream_tournament;

CREATE DATABASE IF NOT EXISTS dream_tournament;

USE dream_tournament;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(255) NOT NULL,
    level INT NOT NULL DEFAULT 1,
    coins INT NOT NULL DEFAULT 5000,
    rewards_claimed BOOLEAN NOT NULL DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS tournaments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tournament_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    is_active BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS tournament_group (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
    );

CREATE TABLE IF NOT EXISTS group_participant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    tournament_group_id BIGINT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (tournament_group_id) REFERENCES tournament_group(id)
    );

CREATE TABLE IF NOT EXISTS leaderboard (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    country VARCHAR(255) NOT NULL,
    total_score INT NOT NULL,
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
    );

-- Insert example past tournaments
INSERT INTO tournaments (tournament_name, start_date, is_active)
VALUES
('Tournament-2024-12-01', '2024-12-01', TRUE),
('Tournament-2024-12-15', '2024-12-15', FALSE),
('Tournament-2024-11-27', '2024-12-15', FALSE);

-- Insert example users
INSERT INTO users (username, country, level, coins, rewards_claimed)
VALUES
('Player1', 'Germany', 1, 5000, FALSE),
('Player2', 'Turkey', 1, 5000, FALSE),
('Player3', 'United States', 1, 5000, FALSE),
('Player4', 'France', 1, 5000, FALSE),
('Player5', 'United Kingdom', 1, 5000, FALSE);

