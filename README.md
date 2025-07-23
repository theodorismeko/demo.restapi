# Match Betting REST API

A Spring Boot REST API for managing sports matches and betting odds.

## Features

- Full CRUD operations for matches and match odds
- Support for Football and Basketball sports
- H2 in-memory database for development
- PostgreSQL support for production
- RESTful API design
- Docker support
- OpenAPI 3.0 documentation with Swagger UI
- Input validation
- Comprehensive error handling

## Requirements

- Java 21
- Maven 3.6+
- Docker (optional)

## Database Configuration

### PostgreSQL Setup
The application is configured to use PostgreSQL as the primary database. When running with Docker Compose, PostgreSQL is automatically set up with:
- Database name: `matches`
- Username: `postgres`
- Password: `password`
- Port: `5432`

### Database Initialization
A custom initialization script is mounted at `/docker/init.sql` that runs when the PostgreSQL container first starts. You can add custom SQL commands for:
- Creating initial tables
- Inserting seed data
- Setting up database extensions

### Application Properties
The key database configurations in `application.properties`:
```properties
spring.jpa.database=POSTGRESQL
spring.datasource.url=jdbc:postgresql://localhost:5432/matches
spring.jpa.hibernate.ddl-auto=update
```

## Getting Started

### Running locally

1. Clone the repository
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access the API at http://localhost:8080/api
4. Access H2 console at http://localhost:8080/h2-console
5. Access Swagger UI at http://localhost:8080/swagger-ui.html

### Running with Docker Compose (Recommended)

```bash
# Start all services (PostgreSQL, pgAdmin, and the application)
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes (clean start)
docker-compose down -v
```

#### Service URLs
- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **pgAdmin**: http://localhost:5050
  - Email: `meko@mail.com`
  - Password: `admin`

### Running with Docker (Standalone)

```bash
docker build -t match-api .
docker run -p 8080:8080 match-api
```

## API Documentation

### OpenAPI/Swagger
- **Interactive API Documentation**: http://localhost:8080/swagger-ui.html
- **OpenAPI Specification**: http://localhost:8080/api-docs

### Static Documentation
See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for complete API documentation.

## Sample API Calls

### Create a match
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

### Get all matches
```bash
curl http://localhost:8080/api/matches
```

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/meko/restapi/
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # JPA entities
│   │   │   ├── exception/      # Exception handlers
│   │   │   ├── repository/     # JPA repositories
│   │   │   └── service/        # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker/
│   └── init.sql               # PostgreSQL initialization script
├── docker-compose.yml         # Docker Compose configuration
├── Dockerfile                 # Application Docker image
└── pom.xml                    # Maven configuration
```

## Technologies Used

- Spring Boot 3.5.3
- Spring Data JPA
- H2 Database (development - disabled by default)
- PostgreSQL (primary database)
- Docker & Docker Compose
- pgAdmin 4
- Lombok
- Maven
