package com.example.todo

import com.example.todo.api.*
import com.example.todo.domain.TodoItem
import com.example.todo.domain.TodoStatus
import com.example.todo.endpoint.CreateTodoRequest
import com.example.todo.endpoint.TodoEndpoint
import io.fluxzero.sdk.test.TestFixture
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TodoTest {

    private val testFixture = TestFixture.create()
    private val todoId = TodoId()

    @Test
    fun createTodo() {
        testFixture.whenCommand(
            CreateTodo(
                todoId = todoId,
                title = "Learn FluxZero",
                description = "Build a todo app with FluxZero",
                assignee = "john.doe",
                dueDate = LocalDate.now().plusDays(7)
            )
        ).expectResult { result: TodoItem ->
            result.todoId == todoId &&
            result.title == "Learn FluxZero" &&
            result.description == "Build a todo app with FluxZero" &&
            result.assignee == "john.doe" &&
            result.status == TodoStatus.PENDING
        }
    }

    @Test
    fun createDuplicateTodoIsRejected() {
        val createCommand = CreateTodo(
            todoId = todoId,
            title = "Test Todo",
            description = "Test description"
        )

        testFixture.givenCommands(createCommand)
            .whenCommand(createCommand)
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun completeTodo() {
        testFixture.givenCommands(
            CreateTodo(
                todoId = todoId,
                title = "Test Todo",
                description = "Test description"
            )
        ).whenCommand(CompleteTodo(todoId))
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.COMPLETED
            }
    }

    @Test
    fun completeAlreadyCompletedTodoIsRejected() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo"),
            CompleteTodo(todoId)
        ).whenCommand(CompleteTodo(todoId))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun uncompleteTodo() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo"),
            CompleteTodo(todoId)
        ).whenCommand(UncompleteTodo(todoId))
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.PENDING
            }
    }

    @Test
    fun uncompleteNonCompletedTodoIsRejected() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo")
        ).whenCommand(UncompleteTodo(todoId))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun renameTodo() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Original Title", description = "Original description")
        ).whenCommand(RenameTodo(todoId, "New Title", "New description"))
            .expectResult { result: TodoItem ->
                result.title == "New Title" &&
                result.description == "New description"
            }
    }

    @Test
    fun renameWithNoChangesIsRejected() {
        val createCommand = CreateTodo(todoId = todoId, title = "Same Title", description = "Same description")
        testFixture.givenCommands(createCommand)
            .whenCommand(RenameTodo(todoId, "Same Title", "Same description"))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun assignTodo() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo")
        ).whenCommand(AssignTodo(todoId, "jane.doe"))
            .expectResult { result: TodoItem ->
                result.assignee == "jane.doe"
            }
    }

    @Test
    fun assignToSameUserIsRejected() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo", assignee = "john.doe")
        ).whenCommand(AssignTodo(todoId, "john.doe"))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun setDueDate() {
        val newDueDate = LocalDate.now().plusDays(10)
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo")
        ).whenCommand(SetDueDate(todoId, newDueDate))
            .expectResult { result: TodoItem ->
                result.dueDate == newDueDate
            }
    }

    @Test
    fun setSameDueDateIsRejected() {
        val dueDate = LocalDate.now().plusDays(5)
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo", dueDate = dueDate)
        ).whenCommand(SetDueDate(todoId, dueDate))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun removeTodo() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo")
        ).whenCommand(RemoveTodo(todoId))
            .expectResult { result: TodoItem? ->
                result == null
            }
    }

    @Test
    fun removeNonExistentTodoIsRejected() {
        testFixture.whenCommand(RemoveTodo(todoId))
            .expectExceptionalResult(IllegalCommandException::class.java)
    }

    @Test
    fun getTodo() {
        testFixture.givenCommands(
            CreateTodo(todoId = todoId, title = "Test Todo", description = "Test description")
        ).whenQuery(GetTodo(todoId))
            .expectResult { result: TodoItem? ->
                result?.title == "Test Todo" &&
                result.description == "Test description"
            }
    }

    @Test
    fun getNonExistentTodoReturnsNull() {
        testFixture.whenQuery(GetTodo(todoId))
            .expectResult { result: TodoItem? -> result == null }
    }

    @Test
    fun completeWorkflow() {
        testFixture
            .whenCommand(CreateTodo(todoId = todoId, title = "Complete Workflow Test"))
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.PENDING
            }
            .andThen()
            .whenCommand(AssignTodo(todoId, "test.user"))
            .expectResult { result: TodoItem ->
                result.assignee == "test.user"
            }
            .andThen()
            .whenCommand(SetDueDate(todoId, LocalDate.now().plusDays(3)))
            .expectResult { result: TodoItem ->
                result.dueDate != null
            }
            .andThen()
            .whenCommand(CompleteTodo(todoId))
            .expectResult { result: TodoItem ->
                result.status == TodoStatus.COMPLETED
            }
    }

    @Nested
    inner class TodoEndpointTests {

        @BeforeEach
        fun setUp() {
            testFixture.registerHandlers(TodoEndpoint())
        }

        @Test
        fun createTodoViaEndpoint() {
            val request = CreateTodoRequest(
                title = "REST API Test",
                description = "Testing todo creation via REST",
                assignee = "api.user",
                dueDate = LocalDate.now().plusDays(7)
            )

            testFixture.whenPost("/todos", request)
                .expectResult(TodoId::class.java)
                .expectEvents(CreateTodo::class.java)
        }

        @Test
        fun listTodosViaEndpoint() {
            testFixture.whenGet("/todos")
                .expectResult<List<TodoItem>> { result -> result.isEmpty() }
        }
    }
}