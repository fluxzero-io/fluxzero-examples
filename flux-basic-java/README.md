# Fluxzero Basic Java Example

A foundational Fluxzero application demonstrating core concepts including user management, role-based authentication, and REST/WebSocket APIs using Java and Spring Boot.

## What's Included

This example demonstrates:

- **User Management System**: Complete CRUD operations for user profiles
- **Role-Based Authentication**: User roles (ADMIN, MANAGER, USER) with permission checking
- **REST API Endpoints**: HTTP endpoints for user operations
- **Event Sourcing**: Fluxzero's event-driven architecture
- **Command Query Separation**: Separate command and query handlers
- **Spring Boot Integration**: Full Spring ecosystem integration

### Key Components

- **Users API** (`com.example.app.user.*`): User creation, profile management, role assignment
- **Authentication** (`com.example.app.authentication.*`): Role-based access control
- **API Models**: Request/response objects and domain models
- **Command Handlers**: Business logic for user operations
- **Query Handlers**: Data retrieval and projection logic

## Running the Application

### Prerequisites

- Java 24 or higher
- Maven 3.6.3+ or Gradle 8.0+

### Quick Start

Start the complete application stack (Test Server + Proxy + Application) with a single command:

**Using Gradle:**
```bash
./gradlew runTestApp
```

**Using Maven:**
```bash
mvn exec:java
```

**Using IntelliJ IDEA:**
- Use the provided run configurations to start the TestApp directly from the IDE

This will start:
- **Fluxzero Test Server** on port 8888
- **Fluxzero Proxy** on port 8080
- **Spring Boot Application** on port 8080 (proxied)

## API Endpoints

Once running, try these endpoints:

- **GET** `/api/users` - List all users
- **POST** `/api/users` - Create new user
- **PUT** `/api/users/{id}/role` - Set user role
- **GET** `/api/users/{id}` - Get user details

## Testing

Run the test suite:
```bash
./gradlew test
# or
mvn test
```

## Architecture

This example follows Fluxzero's event-driven architecture:

1. **Commands** trigger business operations
2. **Events** capture state changes
3. **Queries** project current state
4. **Event Store** provides persistence and audit trail
5. **Projections** maintain read-optimized views

## Next Steps

- Explore the source code in `src/main/java/com/example/app/`
- Check out the test examples in `src/test/java/`
- Modify user roles and permissions
- Add new API endpoints
- Implement custom business logic
