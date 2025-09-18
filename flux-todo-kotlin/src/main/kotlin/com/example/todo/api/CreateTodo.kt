package com.example.todo.api

import com.example.todo.domain.TodoItem
import com.example.todo.domain.TodoStatus
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalDateTime

data class CreateTodo(
    @field:NotNull override val todoId: TodoId,
    @field:NotBlank val title: String,
    val description: String? = null,
    val assignee: String? = null,
    val dueDate: LocalDate? = null
) : TodoCommand, Request<TodoItem> {

    @AssertLegal
    fun assertNewTodo(todo: TodoItem?) {
        if (todo != null) {
            throw IllegalCommandException("Todo already exists")
        }
    }

    @Apply
    fun apply(): TodoItem {
        return TodoItem.builder()
            .todoId(todoId)
            .title(title)
            .description(description)
            .assignee(assignee)
            .dueDate(dueDate)
            .status(TodoStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build()
    }
}