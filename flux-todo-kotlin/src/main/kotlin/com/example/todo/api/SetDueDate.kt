package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import io.fluxzero.sdk.tracking.handling.Request
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalDateTime

data class SetDueDate(
    @field:NotNull override val todoId: TodoId,
    val dueDate: LocalDate?
) : TodoCommand, Request<TodoItem> {

    @AssertLegal
    fun assertTodoExists(todo: TodoItem?) {
        if (todo == null) {
            throw IllegalCommandException("Todo does not exist")
        }
    }

    @AssertLegal
    fun assertDueDateChanged(todo: TodoItem?) {
        if (todo?.dueDate == dueDate) {
            throw IllegalCommandException("Due date is already set to this value")
        }
    }

    @Apply
    fun apply(todo: TodoItem): TodoItem {
        return todo.toBuilder()
            .dueDate(dueDate)
            .updatedAt(LocalDateTime.now())
            .build()
    }
}