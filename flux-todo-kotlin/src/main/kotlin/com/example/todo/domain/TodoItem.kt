package com.example.todo.domain

import com.example.todo.api.TodoId
import io.fluxzero.sdk.modeling.Aggregate
import io.fluxzero.sdk.modeling.EventPublication
import java.time.LocalDate
import java.time.LocalDateTime

@Aggregate(searchable = true, eventPublication = EventPublication.IF_MODIFIED)
data class TodoItem(
    val todoId: TodoId,
    val title: String,
    val description: String? = null,
    val assignee: String? = null,
    val dueDate: LocalDate? = null,
    val status: TodoStatus = TodoStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        @JvmStatic
        fun builder(): TodoItemBuilder = TodoItemBuilder()
    }

    fun toBuilder(): TodoItemBuilder = TodoItemBuilder()
        .todoId(todoId)
        .title(title)
        .description(description)
        .assignee(assignee)
        .dueDate(dueDate)
        .status(status)
        .createdAt(createdAt)
        .updatedAt(updatedAt)

    fun isOverdue(): Boolean {
        return dueDate?.let { it.isBefore(LocalDate.now()) && status != TodoStatus.COMPLETED } ?: false
    }

    fun isCompleted(): Boolean = status == TodoStatus.COMPLETED
}

class TodoItemBuilder {
    private var todoId: TodoId? = null
    private var title: String? = null
    private var description: String? = null
    private var assignee: String? = null
    private var dueDate: LocalDate? = null
    private var status: TodoStatus = TodoStatus.PENDING
    private var createdAt: LocalDateTime = LocalDateTime.now()
    private var updatedAt: LocalDateTime = LocalDateTime.now()

    fun todoId(todoId: TodoId): TodoItemBuilder {
        this.todoId = todoId
        return this
    }

    fun title(title: String): TodoItemBuilder {
        this.title = title
        return this
    }

    fun description(description: String?): TodoItemBuilder {
        this.description = description
        return this
    }

    fun assignee(assignee: String?): TodoItemBuilder {
        this.assignee = assignee
        return this
    }

    fun dueDate(dueDate: LocalDate?): TodoItemBuilder {
        this.dueDate = dueDate
        return this
    }

    fun status(status: TodoStatus): TodoItemBuilder {
        this.status = status
        return this
    }

    fun createdAt(createdAt: LocalDateTime): TodoItemBuilder {
        this.createdAt = createdAt
        return this
    }

    fun updatedAt(updatedAt: LocalDateTime): TodoItemBuilder {
        this.updatedAt = updatedAt
        return this
    }

    fun build(): TodoItem {
        return TodoItem(
            todoId = todoId ?: throw IllegalStateException("todoId is required"),
            title = title ?: throw IllegalStateException("title is required"),
            description = description,
            assignee = assignee,
            dueDate = dueDate,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}