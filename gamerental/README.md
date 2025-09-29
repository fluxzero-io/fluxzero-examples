# 🎮 Game Rental Demo — Powered by Flux Capacitor

This repository demonstrates how to build a clean, reactive, and fully testable application using the [Flux Capacitor](https://fluxzero.io) Java client.

You'll find a complete, production-like system for managing and renting games, showing off:

---

## 🚀 Features

### ✅ Zero-Boilerplate Command Handling
Send commands to an **event-sourced** aggregate with almost no glue code:

```java
Fluxzero.sendCommand(new RegisterGame(...));
```

The game entity handles and validates updates via `@Apply` and `@AssertLegal`, no other frameworks or magic base classes required.

---

### 🔍 Live Search & Queries
Use the **document store** for indexed queries:

- `FindGames` searches game titles/tags.
- `GetGameStats` computes **faceted search statistics** like genre distribution.

---

### ⚖️ Constraints, Roles, and Guardrails
All game commands are checked for:

- ✋ Violations (via JSR-380 annotations)
- 🔒 Required roles (`@RequiresRole`)
- 🧩 Business rules (`@AssertLegal`)

If a command is invalid, a detailed error is returned automatically.

---

### 📦 Dynamic DLQ & Error Recovery

The `FlakyGameAnnouncer` is intentionally unstable — it sometimes throws exceptions during event handling.  
When this happens, Flux automatically logs the error, including a correlation to the **causing message**, enabling a **Dynamic Dead Letter Queue (DLQ)**.

The `CorrectingGameAnnouncer` listens to the error log and reacts accordingly by correcting the error.

All of this is transparent, testable, and fully integrated into the message tracking system.

---

### 🌐 Clean Web API with Zero boilerplate
Use `@HandleGet`, `@HandlePost`, and friends to expose endpoints like:

- `POST /games` → registers a new game
- `GET /games?term=puzzle` → search
- `POST /games/{gameId}/rent` → rent a game
- `POST /games/{gameId}/return` → return a game

Handlers are type-safe and declarative — no need for annotations like `@RestController` or manual JSON parsing.

---

### 🔄 WebSockets and Live Updates
Clients can open a **WebSocket** connection (via `GameSocket`) to receive real-time updates whenever a new game is added.

---

### 🧪 Ultra-Compact Tests
Test all flows — commands, queries, events, errors, and web requests — using the Flux Capacitor testing framework.

No mocks, no fakes. Just:

```java
testFixture.whenPost("/games", "/game/game-details.json")
           .expectResult(GameId.class)
           .expectEvents(RegisterGame.class);
```

Fixtures auto-fill path params and simulate end-to-end behavior across messaging layers.

---

## 📂 Project Structure

```
src/
├── main/
│   └── java/com/example/app/
│       ├── authentication/     # Role-based access control
│       └── gamerental/
│           ├── announcer/      # Announcers and DLQ correction
│           └── api/            # Commands and queries
│               └── common/     # Value types
├── test/
│   └── java/com/example/app/
│       └── gamerental/         # Game and announcer tests
│       └── AppIntegrationTest  # Full system test
└── resources/
    └── game/                   # JSON payloads for test fixtures
```

---

## 🛠️ Running the App

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

### Testing

Run the test suite:
```bash
./gradlew test
# or
mvn test
```

---

## 🤔 Why Flux Capacitor?

Because message-driven, event-sourced, location-transparent systems shouldn’t be this hard.

Flux Capacitor makes it:

- **Concise**: No boilerplate aggregates or DTOs
- **Safe**: Built-in validation, retry, DLQ, snapshots
- **Flexible**: Web, CLI, schedule, WebSocket, documents — all unified
- **Testable**: Verify behavior through message effects, not mocks

---

## 📎 Related Docs

- [Flux Capacitor Java Client README](https://github.com/fluxzero-io/fluxzero-client)
- [Introduction to Message Handling](https://fluxzero.io/docs/message-handling)
- [DLQ and Error Recovery](https://fluxzero.io/docs/dlq)
- [Test Fixtures](https://fluxzero.io/docs/testing)

---

Enjoy renting games the event-sourced way 🎮✨