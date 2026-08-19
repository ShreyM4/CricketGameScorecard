# Cricket Game Scorecard Engine API

A clean, robust Spring Boot 3 + MongoDB REST API for a Single-Innings Cricket Scorecard Engine.

## Features
- **Player Management**: Create and retrieve players with roles (`BATSMAN`, `BOWLER`, etc.).
- **Live Match Tracking**: Start new single-innings matches with custom team names, total overs, striker, and non-striker.
- **Ball-by-Ball Engine**:
  - Dynamically updates runs, wickets, and overs count.
  - Formats overs cleanly (e.g. `0.1` through `0.5`, resetting to `1.0`).
  - Handles extras (Wide / No-Ball) without incrementing valid ball count.
  - Automatic strike swap logic on odd runs scored (`1`, `3`, `5`) and over completion (`6` valid balls).
  - Automatically completes match when 10 wickets fall or total overs are completed.
- **Live Scorecard API**: Retrieve full match state in real time.

## Technology Stack
- Java 21
- Spring Boot 3.3.2
- Spring Data MongoDB
- JUnit 5 & Mockito


## API Endpoints

### 1. Create a Player
- **POST** `/api/players`
- **Body**:
  ```json
  {
    "name": "Virat Kohli",
    "role": "BATSMAN"
  }
  ```

### 2. Start a New Match
- **POST** `/api/matches`
- **Body**:
  ```json
  {
    "battingTeam": "IND",
    "bowlingTeam": "AUS",
    "totalOvers": 20,
    "strikerId": "p1",
    "nonStrikerId": "p2"
  }
  ```

### 3. Record Ball Event
- **POST** `/api/matches/{id}/ball`
- **Body**:
  ```json
  {
    "runsScored": 1,
    "isWicket": false,
    "isExtra": false
  }
  ```

### 4. Get Live Scorecard
- **GET** `/api/matches/{id}`
- **Response Example**:
  ```json
  {
    "id": "66b8d8a7c2b3e41234567890",
    "battingTeam": "IND",
    "bowlingTeam": "AUS",
    "totalOvers": 20,
    "totalRuns": 1,
    "totalWickets": 0,
    "oversBowled": 0.1,
    "validBalls": 1,
    "status": "LIVE",
    "strikerId": "p2",
    "nonStrikerId": "p1",
    "recentBalls": [
      {
        "runsScored": 1,
        "isWicket": false,
        "isExtra": false
      }
    ]
  }
  ```

## Running the Application
Ensure MongoDB is running locally on port `27017` (or update `src/main/resources/application.yml`).

```bash
# Run tests
mvn clean test

# Run application
mvn spring-boot:run
```
