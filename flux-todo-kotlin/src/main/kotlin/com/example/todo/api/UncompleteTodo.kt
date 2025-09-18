package com.example.todo.api

import com.example.todo.domain.TodoItem
import com.example.todo.domain.TodoStatus
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class UncompleteTodo(
    @field:NotNull override val todoId: TodoId
) : TodoCommand, Request<TodoItem> {

    @AssertLegal
    fun assertTodoExists(todo: TodoItem?) {
        if (todo == null) {
            throw IllegalCommandException("Todo does not exist")
        }
    }

    @AssertLegal
    fun assertCurrentlyCompleted(todo: TodoItem?) {
        if (todo?.isCompleted() != true) {
            throw IllegalCommandException("Todo is not currently completed")
        }
    }

    @Apply
    fun apply(todo: TodoItem): TodoItem {
        return todo.toBuilder()
            .status(TodoStatus.PENDING)
            .updatedAt(LocalDateTime.now())
            .build()
    }
}