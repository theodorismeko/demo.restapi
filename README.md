# Match Betting REST API

A comprehensive Spring Boot REST API for managing sports matches and betting odds with modern development practices, and test coverage.

## 🚀 Features

- **Full CRUD operations** for matches and match odds
- **Multi-sport support** (Football and Basketball)
- **PostgreSQL database** with Docker support
- **RESTful API design** with proper HTTP status codes
- **Docker containerization** with Docker Compose
- **OpenAPI 3.0 documentation** with interactive Swagger UI
- **Input validation** with Bean Validation
- **Global exception handling** with custom error responses
- **Comprehensive logging** with configurable levels
- **Database migration** support with Hibernate DDL
- **🆕 Complete test suite** with unit and integration tests
- **🆕 Code coverage reporting** with JaCoCo
- **🆕 Multiple database support** (PostgreSQL for production, H2 for testing)

## 📋 Requirements

- **Java 21** (LTS)
- **Maven 3.6+**
- **Docker & Docker Compose** (recommended for development)
- **PostgreSQL 15+** (handled by Docker Compose)

## 🏗️ Architecture

The application follows a clean layered architecture:
```
┌─────────────────┐
│   Controllers   │ ← REST endpoints & validation
├─────────────────┤
│    Services     │ ← Business logic & transactions
├─────────────────┤
│  Repositories   │ ← Data access layer
├─────────────────┤
│    Entities     │ ← JPA entities & relationships
└─────────────────┘
```

## 🗄️ Database Schema

### Entities
- **Match**: Core match information (teams, date, time, sport)
- **MatchOdds**: Betting odds associated with matches
- **Sport**: Enumeration for supported sports (FOOTBALL, BASKETBALL)

### Relationships
- One Match can have multiple MatchOdds (One-to-Many)
- Cascading delete: removing a match removes all associated odds

## 🗄️ Database Configuration

### PostgreSQL Setup
The application is configured to use PostgreSQL as the primary database. When running with Docker Compose, PostgreSQL is automatically set up with:
- Database name: `matches`
- Username: `postgres`
- Password: `password`
- Port: `5432`

### 🆕 Multi-Database Support
- **Production**: PostgreSQL with full persistence
- **Testing**: H2 in-memory database for fast test execution

### Database Initialization
A custom initialization script is mounted at `/docker/init.sql` that runs when the PostgreSQL container first starts. You can add custom SQL commands for:
- Creating initial tables
- Inserting seed data
- Setting up database extensions

### Application Properties
The key database configurations in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/matches
spring.jpa.hibernate.ddl-auto=update
```

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd demo.restapi
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - **API**: http://localhost:8080/api
   - **Swagger UI**: http://localhost:8080/swagger-ui.html
   - **pgAdmin**: http://localhost:5050 (email: `meko@mail.com`, password: `admin`)

4. **View logs**
   ```bash
   docker-compose logs -f app
   ```

5. **Stop services**
   ```bash
   docker-compose down
   ```

### Option 2: Local Development

1. **Start PostgreSQL**
   ```bash
   docker-compose up -d postgres
   ```

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the application at** http://localhost:8088

## 📚 API Documentation

### Interactive Documentation
- **Swagger UI**: http://localhost:8088/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8088/api-docs

### Endpoints Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/matches` | Create a new match |
| `GET` | `/api/matches` | Get all matches |
| `GET` | `/api/matches/{id}` | Get match by ID |
| `PUT` | `/api/matches/{id}` | Update match |
| `DELETE` | `/api/matches/{id}` | Delete match |
| `POST` | `/api/match-odds` | Create match odds |
| `GET` | `/api/match-odds/{id}` | Get odds by ID |
| `GET` | `/api/match-odds/match/{matchId}` | Get odds by match ID |
| `PUT` | `/api/match-odds/{id}` | Update match odds |
| `DELETE` | `/api/match-odds/{id}` | Delete match odds |

## 🧪 Sample API Usage

### Create a Match
```bash
curl -X POST http://localhost:8088/api/matches \
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

### Create Match Odds
```bash
curl -X POST http://localhost:8088/api/match-odds \
  -H "Content-Type: application/json" \
  -d '{
    "matchId": 1,
    "specifier": "1",
    "odd": 2.5
  }'
```

### Get All Matches
```bash
curl http://localhost:8088/api/matches
```

## 🗂️ Project Structure

```
demo.restapi/
├── src/
│   ├── main/
│   │   ├── java/com/meko/restapi/
│   │   │   ├── Application.java           # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── OpenAPIConfig.java     # Swagger/OpenAPI configuration
│   │   │   ├── controller/
│   │   │   │   ├── MatchController.java   # Match REST endpoints
│   │   │   │   └── MatchOddsController.java # Match odds REST endpoints
│   │   │   ├── dto/
│   │   │   │   ├── MatchDTO.java          # Match data transfer object
│   │   │   │   └── MatchOddsDTO.java      # Match odds data transfer object
│   │   │   ├── entity/
│   │   │   │   ├── Match.java             # Match JPA entity
│   │   │   │   └── MatchOdds.java         # Match odds JPA entity
│   │   │   ├── enumeration/
│   │   │   │   └── Sport.java             # Sport enumeration
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java # Global error handling
│   │   │   │   └── ResourceNotFoundException.java # Custom exception
│   │   │   ├── repository/
│   │   │   │   ├── MatchRepository.java   # Match JPA repository
│   │   │   │   └── MatchOddsRepository.java # Match odds JPA repository
│   │   │   ├── service/
│   │   │   │   ├── MatchService.java      # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── MatchServiceImpl.java # Service implementation
│   │   │   └── util/                      # 🆕 Utility classes
│   │   └── resources/
│   │       ├── application.properties     # Application configuration
│   │       └── application-test.properties # 🆕 Test configuration
│   └── test/                             # 🆕 Comprehensive test suite
│       ├── java/com/meko/restapi/
│       │   ├── ApplicationTests.java     # Application context test
│       │   ├── controller/
│       │   │   └── MatchControllerIntegrationTest.java # Integration tests
│       │   └── service/
│       │       └── MatchServiceTest.java # Unit tests
│       └── resources/
│           └── application-test.properties
├── docker/
│   └── init.sql                          # PostgreSQL initialization script
├── target/                              # 🆕 Build outputs
│   ├── site/jacoco/                     # Code coverage reports
│   ├── surefire-reports/                # Test reports
│   └── test-classes/                    # Compiled test classes
├── docker-compose.yml                   # Multi-container Docker app
├── Dockerfile                          # Application container definition
├── pom.xml                            # Maven dependencies and build config
├── DESIGN_ARCHITECTURE.md            # Architecture and design decisions
└── README.md                         # This file
```

## ⚙️ Configuration

### Database Configuration
The application uses PostgreSQL as the primary database. Key configurations in `application.properties`:

```properties
# PostgreSQL Database
spring.datasource.url=jdbc:postgresql://localhost:5432/matches
spring.datasource.username=postgres
spring.datasource.password=password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 🆕 Test Configuration
Separate test configuration in `application-test.properties`:
```properties
# H2 In-Memory Database for Testing
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
```

### Environment Variables
When running with Docker, the following environment variables are used:

```yaml
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/matches
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password
```

## 🧪 Testing

### 🆕 Comprehensive Test Suite

The project now includes a complete testing strategy:

#### Unit Tests
- **Service Layer Tests**: `MatchServiceTest.java`
- **Business Logic Validation**: Isolated component testing
- **Mock Dependencies**: Using Mockito for external dependencies

#### Integration Tests
- **Controller Integration**: `MatchControllerIntegrationTest.java`
- **End-to-End API Testing**: Full request/response cycle testing
- **Database Integration**: Using Testcontainers with real PostgreSQL

#### Test Commands
```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw clean test jacoco:report

# Run specific test class
./mvnw test -Dtest=MatchServiceTest

# Run integration tests only
./mvnw test -Dtest=*IntegrationTest
```

### 🆕 Code Coverage
- **JaCoCo Integration**: Automatic code coverage reporting
- **Coverage Reports**: Available in `target/site/jacoco/index.html`
- **Coverage Metrics**: Line, branch, and method coverage tracking

#### View Coverage Report
```bash
# Generate coverage report
./mvnw clean test jacoco:report

# Open coverage report (Windows)
start target/site/jacoco/index.html

# Open coverage report (Linux/Mac)
open target/site/jacoco/index.html
```


## 🛠️ Development

### Build the Application
```bash
./mvnw clean compile
```

### Package as JAR
```bash
./mvnw clean package
```

### Run with Maven
```bash
./mvnw spring-boot:run
```

### 🆕 Development with Coverage
```bash
# Run with test coverage monitoring
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Docker Build
```bash
docker build -t match-betting-api .
```

## 🔧 Technology Stack

| Category | Technology |
|----------|------------|
| **Framework** | Spring Boot 3.5.3 |
| **Language** | Java 21 |
| **Database** | PostgreSQL 15 / H2 (testing) |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven |
| **Documentation** | SpringDoc OpenAPI 3 |
| **Containerization** | Docker & Docker Compose |
| **Database Admin** | pgAdmin 4 |
| **Code Reduction** | Lombok |
| **Validation** | Bean Validation (JSR-303) |
| **🆕 Testing** | JUnit 5, Mockito |
| **🆕 Code Coverage** | JaCoCo |
| **🆕 Test Database** | H2 Database |

## 🚀 Getting Started for Developers

### 1. Quick Setup
```bash
git clone <repository-url>
cd demo.restapi
docker-compose up -d
```

### 2. Development Workflow
```bash
# Start development
./mvnw spring-boot:run

# Run tests during development
./mvnw test -Dtest=*Test

# Check code coverage
./mvnw jacoco:report
```

### 3. Before Committing
```bash
# Run full test suite
./mvnw clean test

# Check code coverage
./mvnw jacoco:report

# Verify build
./mvnw clean package
```

## 👤 Author

**Theodoros Meko**
- Email: mekotheod@gmail.com
- GitHub: [@theodorismeko](https://github.com/theodorismeko)

---

## 🔗 Quick Links

- **[API Documentation](http://localhost:8088/swagger-ui.html)** - Interactive API explorer
- **[Code Coverage Report](target/site/jacoco/index.html)** - Test coverage metrics
- **[Database Admin](http://localhost:5050)** - pgAdmin interface


