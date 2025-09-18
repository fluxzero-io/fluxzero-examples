package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.constraints.NotNull

data class RemoveTodo(
    @field:NotNull override val todoId: TodoId
) : TodoCommand, Request<TodoItem?> {

    @AssertLegal
    fun assertTodoExists(todo: TodoItem?) {
        if (todo == null) {
            throw IllegalCommandException("Todo does not exist")
        }
    }

    @Apply
    fun apply(todo: TodoItem): TodoItem? {
        // Returning null effectively deletes the aggregate
        return null
    }
}