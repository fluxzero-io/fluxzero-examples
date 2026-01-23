# Fluxzero Base Kotlin Project

This is a foundation project for Fluxzero applications. It serves as a base for developers and agents to build new applications.

## AI Agents

AI agents are expected to read all rules in [.aiassistant/rules](.aiassistant/rules) before starting any task in this project.

## What's Included

A foundational application with the following features already configured:
- **User Management**: Basic operations for user profiles
- **Authentication**: Role-based access control (RBAC)
- **Fluxzero Integration**: Pre-configured proxy and test server

## Quick Start

Start the complete application stack:

```bash
./gradlew runTestApp
# or
mvn exec:java
```

This starts:
- **Fluxzero Test Server**
- **Fluxzero Proxy** on port 8080 (main entry point)
- **Spring Boot Application**

## API Endpoints

- **GET** `/api/users` - List all users
- **POST** `/api/users` - Create new user
- **GET** `/api/users/{id}` - Get user details

## Testing

Run the `All tests` run configuration in your IDE.
