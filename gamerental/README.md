# Fluxzero Game Rental Example

This is an example project for Fluxzero applications, demonstrating a game rental system. It is built on top of the [Fluxzero Base Project](../flux-basic-java).

## AI Agents

### Fluxzero Guidelines Required to Follow

**CRITICAL: Before starting any task, you MUST follow all guidance in `.fluxzero/agents/index.md`.**

### Mandatory Read Order (Do Not Skip)

1. `.fluxzero/agents/index.md`
2. `.fluxzero/agents/rules/guidelines.md`
3. `.fluxzero/agents/rules/runtime-interaction.md`
4. Task-specific manuals selected via the decision tree in `guidelines.md`

### Execution Protocol (Mandatory)

#### New Project or Large Phase

At the start of every new project or major project phase, the agent MUST:

1. Create/update a tickable backlog markdown file with a linear list of functionalities only.
2. Keep that backlog updated while work progresses.

This backlog step may be skipped only when the user explicitly requests a different planning format.

#### Non-trivial Task

For every non-trivial task, the agent MUST:

1. Confirm applicable non-negotiable rules.
2. Update the active project/phase backlog for this task (if such a backlog exists).
3. Implement changes.
4. Add/update tests for behavior changes.
5. Run relevant tests.
6. Perform a final refactor/readability pass for the code written for the just-finished feature when the feature is
   non-trivial. Do not expand to unrelated project-wide cleanup unless explicitly requested.
7. Report compliance checks in final output.

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
