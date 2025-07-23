# Design and Architecture Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture Pattern](#architecture-pattern)
3. [Technology Stack](#technology-stack)
4. [Design Decisions](#design-decisions)
5. [Project Structure](#project-structure)
6. [Data Model](#data-model)
7. [API Design](#api-design)
8. [Security Considerations](#security-considerations)
9. [Deployment Strategy](#deployment-strategy)

## Overview

This document outlines the design and architectural decisions made for the Match Betting REST API application. The application is designed to manage sports matches and their associated betting odds, providing a complete CRUD interface through RESTful endpoints.

## Architecture Pattern

### Layered Architecture
The application follows a **3-tier layered architecture** pattern:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (REST Controllers & DTOs)            │
├─────────────────────────────────────────┤
│         Business Logic Layer            │
│    (Services & Interfaces)              │
├─────────────────────────────────────────┤
│         Data Access Layer               │
│    (JPA Repositories & Entities)        │
└─────────────────────────────────────────┘
```

**Benefits:**
- **Separation of Concerns**: Each layer has a specific responsibility
- **Maintainability**: Changes in one layer don't affect others
- **Testability**: Each layer can be tested independently
- **Scalability**: Layers can be scaled independently if needed

## Technology Stack

### Core Technologies
- **Java 21**: Latest LTS version for better performance and modern features
- **Spring Boot 3.5.3**: Provides auto-configuration and embedded server
- **Spring Data JPA**: Simplifies database operations with repository pattern
- **Maven**: Dependency management and build tool

### Database
- **H2 (Development)**: In-memory database for rapid development
- **PostgreSQL (Production)**: Robust, open-source relational database

### Additional Libraries
- **Lombok**: Reduces boilerplate code
- **Jakarta Validation**: Input validation
- **Jackson**: JSON serialization/deserialization

## Design Decisions

### 1. DTO Pattern
**Decision**: Use separate DTO objects for API communication

**Rationale**:
- Decouples internal domain model from external API contract
- Prevents exposing internal entity structure
- Allows API versioning without affecting domain model
- Provides flexibility in API response structure

### 2. Repository Pattern with Spring Data JPA
**Decision**: Use Spring Data JPA repositories

**Rationale**:
- Reduces boilerplate code for CRUD operations
- Provides powerful query derivation from method names
- Built-in pagination and sorting support
- Easy to switch between different JPA providers

### 3. Service Layer
**Decision**: Implement a service layer between controllers and repositories

**Rationale**:
- Encapsulates business logic
- Transaction management at service level
- Reusable business operations
- Easier to test business logic in isolation

### 4. Enum for Sport Type
**Decision**: Use enum instead of integers for sport type

**Rationale**:
- Type safety
- Self-documenting code
- Prevents invalid values
- Easy to extend with new sports

### 5. Bidirectional Relationship Management
**Decision**: Use bidirectional OneToMany/ManyToOne relationship between Match and MatchOdds

**Rationale**:
- Natural domain modeling
- Cascade operations for related entities
- Orphan removal for cleanup
- Efficient queries with proper fetch strategies

### 6. Global Exception Handling
**Decision**: Centralized exception handling with @RestControllerAdvice

**Rationale**:
- Consistent error response format
- Separation of error handling from business logic
- Better user experience with meaningful error messages
- Easier maintenance of error handling logic

### 7. Validation Strategy
**Decision**: Use Jakarta Bean Validation annotations

**Rationale**:
- Declarative validation rules
- Automatic validation in controllers with @Valid
- Reusable validation logic
- Clear validation requirements in code

## Project Structure

```
src/main/java/com/meko/restapi/
├── controller/          # REST endpoints
│   ├── MatchController.java
│   └── MatchOddsController.java
├── dto/                 # Data Transfer Objects
│   ├── MatchDTO.java
│   └── MatchOddsDTO.java
├── entity/              # JPA Entities
│   ├── Match.java
│   └── MatchOdds.java
├── enumeration/           # Enum
│   └── Sport.java 
├── exception/           # Custom exceptions
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/          # Data access layer
│   ├── MatchRepository.java
│   └── MatchOddsRepository.java
├── service/             # Business logic layer
│   ├── MatchService.java
│   └── impl/
│       └── MatchServiceImpl.java
└── Application.java     # Main Spring Boot application
```

### Package Organization Rationale
- **By Layer**: Organized by technical layers rather than features
- **Clear Boundaries**: Each package has a specific responsibility
- **Easy Navigation**: Developers can quickly find components by type
- **Scalability**: New features follow the same structure

## Data Model

### Entity Relationship Diagram

```
┌─────────────────┐          ┌──────────────────┐
│     Match       │          │   MatchOdds      │
├─────────────────┤          ├──────────────────┤
│ id (PK)         │ 1    *   │ id (PK)          │
│ description     │──────────│ match_id (FK)    │
│ match_date      │          │ specifier        │
│ match_time      │          │ odd              │
│ team_a          │          └──────────────────┘
│ team_b          │
│ sport (ENUM)    │
└─────────────────┘
```

### Design Choices:
1. **Auto-generated IDs**: Using IDENTITY strategy for simplicity
2. **Proper Column Naming**: Snake_case for database, camelCase in Java
3. **Constraints**: NOT NULL constraints for data integrity
4. **Lazy Loading**: For performance optimization
5. **Cascade Operations**: ALL for parent-child relationship

## API Design

### RESTful Principles
1. **Resource-Based URLs**: `/api/matches`, `/api/match-odds`
2. **HTTP Verbs**: Proper use of GET, POST, PUT, DELETE
3. **Status Codes**: Appropriate HTTP status codes
4. **Stateless**: No session state on server

### URL Design Pattern
```
/api/matches           # Collection resource
/api/matches/{id}      # Individual resource
/api/match-odds        # Collection resource
/api/match-odds/{id}   # Individual resource
/api/match-odds/match/{matchId}  # Filtered collection
```

### Request/Response Format
- **Content-Type**: application/json
- **Consistent Structure**: All responses follow similar format
- **Error Responses**: Standardized error format with timestamp and details

## Security Considerations

### Current Implementation
1. **Input Validation**: Prevents invalid data entry
2. **Exception Handling**: Prevents information leakage
3. **SQL Injection Protection**: Using parameterized queries via JPA

### Future Enhancements
1. **Authentication**: JWT or OAuth2
2. **Authorization**: Role-based access control
3. **Rate Limiting**: Prevent API abuse
4. **CORS Configuration**: For web client access
5. **HTTPS**: Encrypted communication

## Deployment Strategy

### Development Environment
- **H2 In-Memory Database**: Quick setup, no installation required
- **Spring Boot DevTools**: Hot reload capability
- **Debug Logging**: Detailed logs for troubleshooting

### Production Environment
- **PostgreSQL Database**: Reliable, scalable RDBMS
- **Docker Containerization**: Consistent deployment across environments
- **Environment Variables**: External configuration
- **Health Checks**: Built-in Spring Boot actuator endpoints

### Configuration Management
```properties
# Development (application-dev.properties)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop

# Production (application-prod.properties)
spring.datasource.url=${DATABASE_URL}
spring.jpa.hibernate.ddl-auto=validate
```

## Performance Considerations

1. **Lazy Loading**: Prevents N+1 query problems
2. **Transaction Boundaries**: Properly scoped transactions
3. **DTO Projections**: Only fetch required data
4. **Connection Pooling**: HikariCP (Spring Boot default)
5. **Caching**: Ready for Spring Cache integration

## Monitoring and Logging

1. **SLF4J with Logback**: Flexible logging framework
2. **Log Levels**: Configurable per package
3. **Correlation IDs**: Track requests across services (future)
4. **Metrics**: Spring Boot Actuator ready

## Testing Strategy

1. **Unit Tests**: Service layer with mocked dependencies
2. **Integration Tests**: Repository layer with @DataJpaTest
3. **API Tests**: Controller layer with @WebMvcTest
4. **End-to-End Tests**: Full application context tests

## Future Enhancements

1. **API Versioning**: Support multiple API versions
2. **Pagination**: For large result sets
3. **Filtering and Sorting**: Advanced query capabilities
4. **WebSocket Support**: Real-time odds updates
5. **Event Sourcing**: Audit trail for all changes
6. **Microservices**: Split into smaller services if needed

## Conclusion

This architecture provides a solid foundation for a match betting API that is:
- **Maintainable**: Clear structure and separation of concerns
- **Scalable**: Can handle growth in features and load
- **Testable**: Each component can be tested in isolation
- **Extensible**: Easy to add new features
- **Production-Ready**: Following Spring Boot best practices
