package com.example.todo

import com.example.todo.api.*
import com.example.todo.domain.TodoStatus
import io.fluxzero.sdk.Fluxzero
import java.time.LocalDate

/**
 * Example demonstrating programmatic usage of the Todo application
 * showcasing FluxZero's event sourcing and CQRS capabilities.
 */
object ExampleUsage {

    @JvmStatic
    fun main(args: Array<String>) {
        // This example shows how to use the todo application programmatically
        println("FluxZero Todo Application Example")
        println("=====================================")

        runExample()
    }

    private fun runExample() {
        // 1. Create a new todo
        val todoId = TodoId()
        println("Creating todo with ID: ${todoId.id}")

        val createdTodo = Fluxzero.sendCommandAndWait(
            CreateTodo(
                todoId = todoId,
                title = "Learn FluxZero Event Sourcing",
                description = "Study the framework and build a sample application",
                assignee = "john.doe",
                dueDate = LocalDate.now().plusDays(7)
            )
        )
        println("Created: ${createdTodo.title} (Status: ${createdTodo.status})")

        // 2. Assign to a different user
        val assignedTodo = Fluxzero.sendCommandAndWait(
            AssignTodo(todoId, "jane.smith")
        )
        println("Reassigned to: ${assignedTodo.assignee}")

        // 3. Update the due date
        val updatedTodo = Fluxzero.sendCommandAndWait(
            SetDueDate(todoId, LocalDate.now().plusDays(3))
        )
        println("Updated due date: ${updatedTodo.dueDate}")

        // 4. Rename the todo
        val renamedTodo = Fluxzero.sendCommandAndWait(
            RenameTodo(
                todoId,
                "Master FluxZero Event Sourcing",
                "Complete study and build a production-ready application"
            )
        )
        println("Renamed: ${renamedTodo.title}")

        // 5. Complete the todo
        val completedTodo = Fluxzero.sendCommandAndWait(
            CompleteTodo(todoId)
        )
        println("Completed: ${completedTodo.status}")

        // 6. Query the final state
        val finalTodo = Fluxzero.queryAndWait(GetTodo(todoId))
        finalTodo?.let { todo ->
            println("\nFinal Todo State:")
            println("Title: ${todo.title}")
            println("Description: ${todo.description}")
            println("Assignee: ${todo.assignee}")
            println("Due Date: ${todo.dueDate}")
            println("Status: ${todo.status}")
            println("Created: ${todo.createdAt}")
            println("Updated: ${todo.updatedAt}")
            println("Overdue: ${todo.isOverdue()}")
        }

        // 7. Demonstrate error handling
        println("\nDemonstrating business rule validation:")
        try {
            // Try to complete an already completed todo
            Fluxzero.sendCommandAndWait(CompleteTodo(todoId))
        } catch (e: Exception) {
            println("Expected error: ${e.message}")
        }

        // 8. Create another todo and remove it
        val tempTodoId = TodoId()
        Fluxzero.sendCommandAndWait(
            CreateTodo(tempTodoId, "Temporary Todo", "This will be deleted")
        )
        println("Created temporary todo: ${tempTodoId.id}")

        val removedTodo = Fluxzero.sendCommandAndWait<com.example.todo.domain.TodoItem?>(
            RemoveTodo(tempTodoId)
        )
        println("Removed todo (result should be null): $removedTodo")

        // 9. Query non-existent todo
        val nonExistentTodo = Fluxzero.queryAndWait(GetTodo(tempTodoId))
        println("Query removed todo: $nonExistentTodo")

        println("\nExample completed successfully!")
    }

    /**
     * Example showing batch operations and workflow
     */
    fun workflowExample() {
        val projectTodos = listOf(
            "Set up development environment",
            "Create domain models",
            "Implement commands",
            "Add REST endpoints",
            "Write comprehensive tests",
            "Deploy to production"
        )

        // Create multiple todos for a project
        val todoIds = projectTodos.mapIndexed { index, title ->
            val todoId = TodoId()
            Fluxzero.sendCommandAndWait(
                CreateTodo(
                    todoId = todoId,
                    title = title,
                    assignee = if (index % 2 == 0) "alice" else "bob",
                    dueDate = LocalDate.now().plusDays((index + 1).toLong())
                )
            )
            todoId
        }

        println("Created ${todoIds.size} todos for the project")

        // Complete first two todos
        todoIds.take(2).forEach { todoId ->
            Fluxzero.sendCommandAndWait(CompleteTodo(todoId))
        }

        println("Completed first 2 todos")

        // Query completed vs pending todos
        val allTodos = todoIds.map { Fluxzero.queryAndWait(GetTodo(it)) }
        val completed = allTodos.count { it?.status == TodoStatus.COMPLETED }
        val pending = allTodos.count { it?.status == TodoStatus.PENDING }

        println("Project status: $completed completed, $pending pending")
    }
}