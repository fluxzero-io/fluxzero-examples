package com.example.todo

import com.example.todo.api.CreateTodo
import com.example.todo.domain.TodoItem
import com.example.todo.domain.TodoStatus
import com.example.todo.endpoint.CreateTodoRequest
import com.example.todo.endpoint.TodoEndpoint
import io.fluxzero.sdk.test.TestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TodoJsonFixtureTest {

    private val testFixture = TestFixture.create()

    @Test
    fun createTodoFromJsonFixture() {
        testFixture.whenCommand("/todo/create-todo.json")
            .expectResult { result: TodoItem ->
                result.title == "Learn FluxZero Framework" &&
                result.description == "Study FluxZero event sourcing patterns and build a sample application" &&
                result.assignee == "john.doe" &&
                result.status == TodoStatus.PENDING
            }
    }

    @Test
    fun completeWorkflowWithJsonFixtures() {
        testFixture
            .whenCommand("/todo/create-todo.json")
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.PENDING
            }
            .andThen()
            .whenCommand("/todo/assign-todo.json")
            .expectResult { result: TodoItem ->
                result.assignee == "alice.johnson"
            }
            .andThen()
            .whenCommand("/todo/complete-todo.json")
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.COMPLETED
            }
    }

    @Test
    fun createMultipleTodosFromFixtures() {
        testFixture.givenCommands("/todo/create-todo.json", "/todo/create-urgent-todo.json")
            .whenCommand("/todo/complete-todo.json")
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.COMPLETED &&
                result.todoId.id == "todo-123"
            }
    }

    @Nested
    inner class EndpointJsonTests {

        @BeforeEach
        fun setUp() {
            testFixture.registerHandlers(TodoEndpoint())
        }

        @Test
        fun createTodoViaEndpointWithJsonFixture() {
            testFixture.whenPost("/todos", "/todo/create-todo-request.json")
                .expectEvents(CreateTodo::class.java)
        }

        @Test
        fun endToEndWorkflowWithFixtures() {
            testFixture.givenPost("/todos", "/todo/create-todo-request.json")
                .whenGet("/todos")
                .expectResult<List<TodoItem>> { result -> result.isEmpty() }
        }
    }
}