# Match Betting REST API Documentation

## Overview
This REST API provides endpoints for managing matches and match odds for a sports betting application. The API supports CRUD operations for both matches and their associated betting odds.

## Base URL
```
http://localhost:8080/api
```

## OpenAPI/Swagger Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **OpenAPI YAML**: http://localhost:8080/api-docs.yaml

## Database Access (Development)
H2 Console available at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Data Models

### Match
```json
{
    "id": 1,
    "description": "OSFP-PAO",
    "matchDate": "2021-03-31",
    "matchTime": "12:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL",
    "matchOdds": []
}
```

**Fields:**
- `id`: Unique identifier (auto-generated)
- `description`: Match description
- `matchDate`: Date of the match (YYYY-MM-DD)
- `matchTime`: Time of the match (HH:MM)
- `teamA`: First team name
- `teamB`: Second team name
- `sport`: Sport type ("FOOTBALL" or "BASKETBALL")
- `matchOdds`: List of associated betting odds

### MatchOdds
```json
{
    "id": 1,
    "matchId": 1,
    "specifier": "X",
    "odd": 1.5
}
```

**Fields:**
- `id`: Unique identifier (auto-generated)
- `matchId`: ID of the associated match
- `specifier`: Betting specifier (e.g., "X" for draw, "1" for home win, "2" for away win)
- `odd`: Betting odd value

## API Endpoints

### Match Endpoints

#### 1. Create Match
**POST** `/matches`

**Request Body:**
```json
{
    "description": "OSFP-PAO",
    "matchDate": "2021-03-31",
    "matchTime": "12:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL"
}
```

**Response:** `201 Created`
```json
{
    "id": 1,
    "description": "OSFP-PAO",
    "matchDate": "2021-03-31",
    "matchTime": "12:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL",
    "matchOdds": []
}
```

#### 2. Get All Matches
**GET** `/matches`

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "description": "OSFP-PAO",
        "matchDate": "2021-03-31",
        "matchTime": "12:00",
        "teamA": "OSFP",
        "teamB": "PAO",
        "sport": "FOOTBALL",
        "matchOdds": []
    }
]
```

#### 3. Get Match by ID
**GET** `/matches/{id}`

**Response:** `200 OK`
```json
{
    "id": 1,
    "description": "OSFP-PAO",
    "matchDate": "2021-03-31",
    "matchTime": "12:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL",
    "matchOdds": []
}
```

#### 4. Update Match
**PUT** `/matches/{id}`

**Request Body:**
```json
{
    "description": "OSFP-PAO Updated",
    "matchDate": "2021-03-31",
    "matchTime": "15:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL"
}
```

**Response:** `200 OK`

#### 5. Delete Match
**DELETE** `/matches/{id}`

**Response:** `204 No Content`

### Match Odds Endpoints

#### 1. Create Match Odds
**POST** `/match-odds`

**Request Body:**
```json
{
    "matchId": 1,
    "specifier": "X",
    "odd": 1.5
}
```

**Response:** `201 Created`
```json
{
    "id": 1,
    "matchId": 1,
    "specifier": "X",
    "odd": 1.5
}
```

#### 2. Get Match Odds by ID
**GET** `/match-odds/{id}`

**Response:** `200 OK`
```json
{
    "id": 1,
    "matchId": 1,
    "specifier": "X",
    "odd": 1.5
}
```

#### 3. Get Match Odds by Match ID
**GET** `/match-odds/match/{matchId}`

**Response:** `200 OK`
```json
[
    {
        "id": 1,
        "matchId": 1,
        "specifier": "X",
        "odd": 1.5
    }
]
```

#### 4. Update Match Odds
**PUT** `/match-odds/{id}`

**Request Body:**
```json
{
    "specifier": "X",
    "odd": 1.8
}
```

**Response:** `200 OK`

#### 5. Delete Match Odds
**DELETE** `/match-odds/{id}`

**Response:** `204 No Content`

## Error Responses

### 404 Not Found
```json
{
    "timestamp": "2024-01-23T10:00:00",
    "message": "Match not found with id: 999",
    "status": 404
}
```

### 500 Internal Server Error
```json
{
    "timestamp": "2024-01-23T10:00:00",
    "message": "An error occurred: ...",
    "status": 500
}
```

## Example Usage

### Creating a Match with Odds

1. First, create a match:
```bash
curl -X POST http://localhost:8080/api/matches \
  -H "Content-Type: application/json" \
  -d '{
    "description": "OSFP-PAO",
    "matchDate": "2021-03-31",
    "matchTime": "12:00",
    "teamA": "OSFP",
    "teamB": "PAO",
    "sport": "FOOTBALL"
  }'
```

2. Then, add odds to the match:
```bash
curl -X POST http://localhost:8080/api/match-odds \
  -H "Content-Type: application/json" \
  -d '{
    "matchId": 1,
    "specifier": "X",
    "odd": 1.5
  }'
```

## Running the Application

### Standalone
```bash
./mvnw spring-boot:run
```

### With Docker
```bash
docker build -t match-api .
docker run -p 8080:8080 match-api
```

## Notes
- The application uses an in-memory H2 database by default
- Data is lost when the application restarts (due to `create-drop` setting)
- For production, configure PostgreSQL in `application.properties`
