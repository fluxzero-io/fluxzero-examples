# Fluxzero Game Rental Example

This is an example project for Fluxzero applications, demonstrating a game rental system. It is built on top of the [Fluxzero Base Project](../flux-basic-java).

## AI Agents

AI agents are expected to read all rules in [.aiassistant/rules](.aiassistant/rules) before starting any task in this project.

## What's Included

In addition to the foundational features (User Management, Authentication, and Fluxzero Integration), this example project includes:
- **Game Inventory**: Management of games available for rent.
- **Rental System**: Logic for renting and returning games.
- **Search & Stats**: Search functionality for games and rental statistics.

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

### User Management
- **GET** `/api/users` - List all users
- **POST** `/api/users` - Create new user
- **GET** `/api/users/{id}` - Get user details

### Game Rental
- **GET** `/api/games` - Find games (optional `term` query param)
- **POST** `/api/games` - Register a new game
- **GET** `/api/games/{id}` - Get game details
- **POST** `/api/games/{id}/rent` - Rent a game
- **POST** `/api/games/{id}/return` - Return a game
- **GET** `/api/games/stats` - Get game statistics

## Testing

Run the `All tests` run configuration in your IDE.
