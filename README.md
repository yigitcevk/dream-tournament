# Dream Tournament

Dream Tournament is a Java-based application designed to manage and simulate 
tournaments. It provides Rest API to maintain users progress, functionalities 
for creating tournaments, managing participants and tracking results.

## Table of Contents

- [Architecture](#architecture)
- [API Documentation](#api-documentation)
- [Database Design](#database-design)
- [Running the Project](#running-the-project)


## Architecture
The project follows a package-layered architecture for maintainability and scalability.

      main
         java
            com
               dream_tournament
                  ├───controller     # Handles HTTP requests
                  ├───dto            # Data Transfer Objects (request/response)
                  ├───model          # Entity classes
                  ├───repository     # Data access layer
                  ├───service        # Business logic
         resources                  # Configuration and templates
      test                           # Unit tests

Each layer has a clear responsibility (e.g., controllers handle HTTP requests, services handle business logic, repositories handle data access).
Each layer can be tested independently, ensuring modular and clean code.

Created User & Tournament architecture to generate layers. While User architecture is mainly responsible for User Progress section,
Tournament is mainly responsible for World Cup Tournament section. 

## API Documentation

Postman: Pre-configured collection under api directory for API testing.(Dream Tournament.postman_collection.json)

| Group                | Request        | Name                         |                                                          |
|----------------------|----------------|------------------------------|----------------------------------------------------------|
|                      | WelcomeRequest | GET                          | http://localhost:8080                                    |
| User Progress        | POST           | CreateUserRequest            | http://localhost:8080/api/user                           |
| User Progress        | PUT            | UpdateLevelRequest           | http://localhost:8080/api/user/level                     |
| World Cup Tournament | POST           | EnterTournamentRequest       | http://localhost:8080/api/tournament/enter               |
| World Cup Tournament | PUT            | ClaimRewardRequest           | http://localhost:8080/api/tournament/claim-reward        |
| World Cup Tournament | GET            | GetGroupRankRequest          | http://localhost:8080/api/tournament/group-rank          |
| World Cup Tournament | GET            | GetGroupLeaderboardRequest   | http://localhost:8080/api/tournament/group-leaderboard   |
| World Cup Tournament | GET            | GetCountryLeaderboardRequest | http://localhost:8080/api/tournament/country-leaderboard |

Implementation of data transfer objects can give the detailed 
view of request and response classes.

-> package com.dream_tournament.dto.*

## Database Design

Docker Compose will configure MySQL and application services. 
The compose.yaml file will be triggered when the application is started 
and docker compose will create the necessary database environment 
using the **mysql-db-dump.sql** file.


## Running the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/yigitcevk/dream-tournament.git
2. Go to the directory:
   ```bash
   cd dream-tournament

3. Maven
   ```bash
   ./mvnw spring-boot:run
   ```




