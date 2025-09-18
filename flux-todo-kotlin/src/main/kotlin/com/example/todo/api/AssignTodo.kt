package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class AssignTodo(
    @field:NotNull override val todoId: TodoId,
    val assignee: String?
) : TodoCommand, Request<TodoItem> {

    @AssertLegal
    fun assertTodoExists(todo: TodoItem?) {
        if (todo == null) {
            throw IllegalCommandException("Todo does not exist")
        }
    }

    @AssertLegal
    fun assertAssigneeChanged(todo: TodoItem?) {
        if (todo?.assignee == assignee) {
            throw IllegalCommandException("Todo is already assigned to this user")
        }
    }

    @Apply
    fun apply(todo: TodoItem): TodoItem {
        return todo.toBuilder()
            .assignee(assignee)
            .updatedAt(LocalDateTime.now())
            .build()
    }
}