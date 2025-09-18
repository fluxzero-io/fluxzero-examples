package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class RenameTodo(
    @field:NotNull override val todoId: TodoId,
    @field:NotBlank val newTitle: String,
    val newDescription: String? = null
) : TodoCommand, Request<TodoItem> {

    @AssertLegal
    fun assertTodoExists(todo: TodoItem?) {
        if (todo == null) {
            throw IllegalCommandException("Todo does not exist")
        }
    }

    @AssertLegal
    fun assertTitleChanged(todo: TodoItem?) {
        if (todo?.title == newTitle && todo.description == newDescription) {
            throw IllegalCommandException("No changes detected")
        }
    }

    @Apply
    fun apply(todo: TodoItem): TodoItem {
        return todo.toBuilder()
            .title(newTitle)
            .description(newDescription)
            .updatedAt(LocalDateTime.now())
            .build()
    }
}