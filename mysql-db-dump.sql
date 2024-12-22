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

-- Insert example past tournaments
INSERT INTO tournaments (tournament_name, start_date, is_active)
VALUES
    -- Active Tournament, Tournament of the day
    ('Tournament-2024-12-22', '2024-12-22', TRUE),
    -- Old Tournaments
    ('Tournament-2024-12-01', '2024-12-01', FALSE),
    ('Tournament-2024-12-15', '2024-12-15', FALSE);

-- Insert example users
INSERT INTO users (username, country, level, coins, rewards_claimed)
VALUES
    -- Eligible players for the active tournament
    ('Player1', 'Germany', 21, 6000, TRUE),
    ('Player2', 'Turkey', 25, 7000, TRUE),
    ('Player3', 'United States', 30, 8000, TRUE),
    ('Player4', 'France', 35, 9000, TRUE),
    ('Player5', 'United Kingdom', 26, 11000, TRUE),
    ('Player6', 'Turkey', 40, 24000, TRUE),
    ('Player7', 'France', 27, 13000, TRUE),
    ('Player8', 'United States', 38, 17000, TRUE),
    -- Ineligible players (low level or insufficient coins)
    ('Player9', 'Germany', 19, 9000, TRUE),
    ('Player10', 'Turkey', 18, 4000, TRUE),
    ('Player11', 'France', 2, 6000, TRUE),
    -- Players with unclaimed rewards
    ('Player12', 'United States', 30, 8000, FALSE),
    ('Player13', 'France', 35, 9000, FALSE),
    ('Player14', 'France', 24, 7000, FALSE),
    ('Player15', 'France', 41, 14000, FALSE),
    ('Player16', 'United Kingdom', 33, 16000, FALSE);


INSERT INTO tournament_group (tournament_id)
VALUES
    (1),
    (2),
    (3);

INSERT INTO group_participant (user_id, tournament_group_id, score)
VALUES
-- Group 1
(1, 1, 12),
(2, 1, 3),
(3, 1, 6),
(4, 1, 11),
(5, 1, 7),
-- Group 2
(1, 2, 5),
(7, 2, 3),
(3, 2, 7),
(6, 2, 9),
(8, 2, 6),
-- Group 3
(12, 3, 21),
(13, 3, 10),
(14, 3, 7),
(15, 3, 14),
(16, 3, 13);

