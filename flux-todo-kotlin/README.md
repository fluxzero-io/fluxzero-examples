# FluxZero Todo Application (Kotlin)

A comprehensive todo application built with FluxZero framework showcasing event sourcing, CQRS patterns, and REST API integration in Kotlin.

## Overview

This application demonstrates FluxZero's event-driven architecture with a full-featured todo management system that includes:

- **Creating** todos with title, description, assignee, and due date
- **Removing** todos (soft delete via returning null)
- **Completing/Uncompleting** todos with status management
- **Renaming** todos (updating title and description)
- **Assigning** todos to users
- **Setting due dates** with overdue detection
- **Querying** todos with various filters
- **REST API** endpoints for web access

## Architecture

### Domain Model

- **TodoId**: Value class wrapping UUID for type safety
- **TodoItem**: Event-sourced aggregate with searchable capabilities
- **TodoStatus**: Enum (PENDING, IN_PROGRESS, COMPLETED)

### Commands (Write Operations)

All commands implement the `TodoCommand` interface and use FluxZero's event sourcing:

- `CreateTodo` - Creates a new todo item
- `RemoveTodo` - Deletes a todo (returns null)
- `CompleteTodo` / `UncompleteTodo` - Toggle completion status
- `RenameTodo` - Update title and description
- `AssignTodo` - Assign to a user
- `SetDueDate` - Set or update due date

### Queries (Read Operations)

Type-safe queries implementing `Request<T>` interface:

- `GetTodo` - Retrieve single todo by ID
- `ListTodos` - List all todos with pagination
- `GetTodosByAssignee` - Filter by assigned user
- `GetOverdueTodos` - Find overdue items

### REST API

The `TodoEndpoint` provides HTTP access to all operations:

```
POST   /todos                     - Create todo
GET    /todos/{id}                - Get single todo
GET    /todos                     - List todos
GET    /todos/assignee/{user}     - Get todos by assignee
GET    /todos/overdue             - Get overdue todos
PUT    /todos/{id}/complete       - Mark complete
PUT    /todos/{id}/uncomplete     - Mark incomplete
PUT    /todos/{id}/rename         - Update title/description
PUT    /todos/{id}/assign         - Assign to user
PUT    /todos/{id}/due-date       - Set due date
DELETE /todos/{id}                - Remove todo
```

## FluxZero Features Demonstrated

### Event Sourcing
- `@Aggregate` annotation with searchable capabilities
- `@Apply` methods for state mutations
- `@AssertLegal` for business rule validation
- Event publication on modifications

### Command/Query Separation
- Commands use `TodoCommand` interface with `@HandleCommand`
- Queries implement `Request<T>` with `@HandleQuery`
- Type-safe return types via generics

### Testing
- Comprehensive test suite using `TestFixture`
- JSON fixture files for external test data
- Command chaining with `andThen()`
- Exception testing for business rule violations

### Spring Integration
- `@Component` REST endpoints
- Spring Boot auto-configuration
- Local client for testing

## Usage Examples

### Programmatic Usage

```kotlin
// Create a todo
val todoId = TodoId()
val todo = Fluxzero.sendCommandAndWait(
    CreateTodo(
        todoId = todoId,
        title = "Learn FluxZero",
        description = "Build a sample application",
        assignee = "john.doe",
        dueDate = LocalDate.now().plusDays(7)
    )
)

// Complete the todo
val completedTodo = Fluxzero.sendCommandAndWait(
    CompleteTodo(todoId)
)

// Query the todo
val retrievedTodo = Fluxzero.queryAndWait(
    GetTodo(todoId)
)
```

### REST API Usage

```bash
# Create a todo
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn FluxZero",
    "description": "Build a sample application",
    "assignee": "john.doe",
    "dueDate": "2025-01-31"
  }'

# Complete a todo
curl -X PUT http://localhost:8080/todos/{id}/complete

# Get todos by assignee
curl http://localhost:8080/todos/assignee/john.doe
```

## Running the Application

### Development Mode
```bash
./gradlew runTestApp
```

### Running Tests
```bash
./gradlew test
```

### Building
```bash
./gradlew build
```

## Key FluxZero Concepts

### 1. Aggregate Design
```kotlin
@Aggregate(searchable = true, eventPublication = EventPublication.IF_MODIFIED)
data class TodoItem(
    val todoId: TodoId,
    val title: String,
    // ... other fields
) {
    fun isOverdue(): Boolean =
        dueDate?.let { it.isBefore(LocalDate.now()) && !isCompleted() } ?: false
}
```

### 2. Command Interface Pattern
```kotlin
@TrackSelf
interface TodoCommand {
    val todoId: TodoId

    @HandleCommand
    fun handle(): TodoItem? {
        return Fluxzero.loadAggregate(todoId).assertAndApply(this).get()
    }
}
```

### 3. Business Rule Validation
```kotlin
@AssertLegal
fun assertTodoExists(todo: TodoItem?) {
    if (todo == null) {
        throw IllegalCommandException("Todo does not exist")
    }
}
```

### 4. Type-Safe Queries
```kotlin
data class GetTodo(val todoId: TodoId) : Request<TodoItem?> {
    @HandleQuery
    fun handle(): TodoItem? {
        return Fluxzero.loadAggregate(todoId).get()
    }
}
```

## Project Structure

```
src/main/kotlin/com/example/todo/
├── api/                    # Commands and queries
│   ├── TodoId.kt
│   ├── TodoCommand.kt
│   ├── CreateTodo.kt
│   ├── RemoveTodo.kt
│   ├── CompleteTodo.kt
│   ├── UncompleteTodo.kt
│   ├── RenameTodo.kt
│   ├── AssignTodo.kt
│   ├── SetDueDate.kt
│   ├── GetTodo.kt
│   ├── ListTodos.kt
│   ├── GetTodosByAssignee.kt
│   └── GetOverdueTodos.kt
├── domain/                 # Domain model
│   ├── TodoItem.kt
│   └── TodoStatus.kt
├── endpoint/               # REST endpoints
│   ├── TodoEndpoint.kt
│   └── CreateTodoRequest.kt
└── TodoApp.kt             # Main application

src/test/kotlin/com/example/todo/
├── TodoTest.kt            # Comprehensive tests
├── TodoJsonFixtureTest.kt # JSON fixture tests
└── TestApp.kt             # Test application

src/test/resources/todo/
├── create-todo.json       # Test fixtures
├── create-urgent-todo.json
├── complete-todo.json
├── assign-todo.json
└── create-todo-request.json
```

This todo application serves as a comprehensive example of FluxZero's capabilities, demonstrating event sourcing, CQRS, business rule validation, type safety, and testing best practices in a real-world scenario.