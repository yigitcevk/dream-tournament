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

Designed to manage users and tournaments for a mobile game. 

It features ***user creation*** functionality where new users start with 5,000 coins, level 1, and no active tournament participation. 

Each user is randomly assigned to one of five fixed countries: Turkey, the United States, the United Kingdom, France, or Germany. User data, including level, coins, and country, is persistently stored in a MySQL database within a User model. 

Users can complete levels with ***update level*** request, which increments their level by one and awards them 25 additional coins.

The project also supports a tournament system. Tournaments run daily from ***00:00 to 20:00*** UTC, and a new tournament is automatically created for the next day. 

Tournaments are ***marked as active*** during their runtime, and the most recent one is flagged as the ***latest***. 

Users who meet specific criteria can enroll in tournaments with ***enter tournament request***. To participate, users must be at least level 20 and pay 1,000 coins. 

Group formation for tournaments requires five users, each representing a different country. Once grouped, the user's ***active tournament status*** is set to ***true***, and any subsequent levels completed during the tournament contribute one point to their score.

Leaderboards are a central feature of the tournament system. A group leaderboard ranks users within their tournament group based on scores and includes details such as user ID, username, country, and tournament score. Additionally, a country leaderboard aggregates and ranks the total scores of users representing each country in the tournament. 

Both leaderboards are ***updated in real-time*** and accessible through dedicated requests.

At the end of the tournament, users can claim rewards with ***claim reward request*** based on their group rankings. The first-place user in a group receives 10,000 coins, while the second-place user earns 5,000 coins. Claiming rewards resets the user's ***active tournament status***, enabling them to join new tournaments. The system ensures users cannot join new tournaments if they have unclaimed rewards from previous tournaments.




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
            resources                   # Configuration and templates
         test                           # Unit tests
      compose.yaml                      # docker-compose Implements MySQL configuration and integrates db-dump 
      mysql-db-dump.sql                 # Database volume for proper API Testing

Each layer has a clear responsibility (e.g., controllers handle HTTP requests, services handle business logic, repositories handle data access).
Each layer can be tested independently, ensuring modular and clean code.

The design prioritizes efficient data handling and scalability to support millions of daily users. Entities were created taking into account the constructor based dependency injection pattern.

Created User & Tournament architecture to generate layers. While User architecture is mainly responsible for User Progress section,
Tournament is mainly responsible for World Cup Tournament section.

Command Pattern also taken as an example to encapsulate request and responses in an object
and Separate the sender and receiver of the request

The project emphasizes immutability as a principle in its design to enhance reliability, maintainability, and predictability. 
By making entities immutable wherever possible, we ensure that once an object is created, its state cannot be modified, reducing the risk of unintended side effects or bugs caused by shared mutable state.
We try to prevent that as much as we can.


## API Documentation

Postman: Pre-configured collection under api directory for API testing.(Dream Tournament.postman_collection.json)

| Group                | Request | Name                         |                                                          |
|----------------------|---------|------------------------------|----------------------------------------------------------|
|                      | GET     | WelcomeRequest                          | http://localhost:8080                                    |
| User Progress        | POST    | CreateUserRequest            | http://localhost:8080/api/user                           |
| User Progress        | PUT     | UpdateLevelRequest           | http://localhost:8080/api/user/level                     |
| World Cup Tournament | POST    | EnterTournamentRequest       | http://localhost:8080/api/tournament/enter               |
| World Cup Tournament | PUT     | ClaimRewardRequest           | http://localhost:8080/api/tournament/claim-reward        |
| World Cup Tournament | GET     | GetGroupRankRequest          | http://localhost:8080/api/tournament/group-rank          |
| World Cup Tournament | GET     | GetGroupLeaderboardRequest   | http://localhost:8080/api/tournament/group-leaderboard   |
| World Cup Tournament | GET     | GetCountryLeaderboardRequest | http://localhost:8080/api/tournament/country-leaderboard |

Implementation of data transfer objects can give the detailed 
view of request and response classes.

Because of the schedulers time selection, an example sql injection is needed so I added to the db-dump initialization.
```bash
INSERT INTO tournaments (tournament_name, start_date, is_active, latest)

VALUES

('Tournament-2024-12-24', '2024-12-43', TRUE, TRUE);
```

Tester could enter tournament with multiple requests and claim try to see leaderboards & claim rewards.

-> package com.dream_tournament.dto.*

## Database Design

Docker Compose will configure MySQL and application services. 
The compose.yaml file will be triggered when the application is started 
and docker compose will create the necessary database environment 
using the **mysql-db-dump.sql** file.

The database design of the project focuses on a well-structured relational schema, with four primary entities: User, Tournament, TournamentGroup, and GroupParticipant. Each entity is implemented using JPA annotations to map the data model to a MySQL database. This approach ensures clear relationships between entities, data integrity, and high performance, catering to the demands of the application.

The User entity represents players in the system and is mapped to the users table. Each user has attributes such as a unique identifier (id), username, country, level, coins, and activeTournament. The country attribute is immutable and assigned randomly from a predefined set of countries when the user is created. Default values, such as starting level and coins, are assigned during persistence using the @PrePersist method. The immutability of the country attribute is enforced by marking it as non-updatable, ensuring data consistency and simplifying user management.

The Tournament entity captures the details of daily competitions and is mapped to the tournaments table. Each tournament has attributes such as id, tournamentName, startDate, isActive, and latest. The @PrePersist method initializes the start date to the current date and automatically generates the tournament name based on the start date. The isActive and latest flags ensure proper tracking of the tournament's state, indicating whether it is currently running and if it is the most recent tournament, respectively.

The TournamentGroup entity represents a subset of participants within a specific tournament and is mapped to the tournament_group table. It maintains a one-to-many relationship with the GroupParticipant entity, allowing each group to track its members. Each tournament group is linked to a specific tournament through a many-to-one relationship. This design enables efficient grouping of users into diverse sets, ensuring that each group includes participants from different countries.

The GroupParticipant entity serves as the link between users and tournament groups and is mapped to the group_participant table. It includes attributes such as id, user, tournamentGroup, and score. The user and tournamentGroup attributes establish many-to-one relationships with the User and TournamentGroup entities, respectively. The score attribute tracks a user's performance within a group and is initialized to zero during persistence. This entity plays a critical role in recording user participation and competition results.

The relationships between entities are carefully defined to maintain data integrity and support complex queries. For example, a user can participate in multiple tournament groups, but each group participant entry corresponds to a single user. Similarly, a tournament can have multiple groups, and each group belongs to a single tournament. These relationships are enforced through foreign keys and cascade rules, ensuring that data modifications propagate correctly across related entities.

Immutability is a key design principle applied across the database model. Attributes such as the country field in the User entity and the startDate in the Tournament entity are immutable, providing consistency and preventing unintended modifications. Default values are assigned using lifecycle hooks like @PrePersist, ensuring a predictable state for new records. This immutability contributes to thread safety and simplifies concurrency management, which is crucial for handling millions of daily users.

Overall, this database design ensures a normalized structure with minimal redundancy, clear relationships between entities, and efficient data access patterns. It is well-suited for the high-concurrency demands of the application while maintaining data consistency, scalability, and performance.


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




