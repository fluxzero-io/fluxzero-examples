package com.example.todo.endpoint

import com.example.todo.api.*
import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.web.HandleDelete
import io.fluxzero.sdk.web.HandleGet
import io.fluxzero.sdk.web.HandlePost
import io.fluxzero.sdk.web.HandlePut
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TodoEndpoint {

    @HandlePost("/todos")
    fun createTodo(request: CreateTodoRequest): TodoId {
        val todoId = TodoId()
        Fluxzero.sendCommandAndWait(
            CreateTodo(
                todoId = todoId,
                title = request.title,
                description = request.description,
                assignee = request.assignee,
                dueDate = request.dueDate
            )
        )
        return todoId
    }

    @HandleGet("/todos/{todoId}")
    fun getTodo(todoId: String): TodoItem? {
        return Fluxzero.queryAndWait(GetTodo(TodoId(todoId)))
    }

    @HandleGet("/todos")
    fun listTodos(page: Int = 1, pageSize: Int = 20): List<TodoItem> {
        return Fluxzero.queryAndWait(ListTodos(page, pageSize))
    }

    @HandleGet("/todos/assignee/{assignee}")
    fun getTodosByAssignee(assignee: String, page: Int = 1, pageSize: Int = 20): List<TodoItem> {
        return Fluxzero.queryAndWait(GetTodosByAssignee(assignee, page, pageSize))
    }

    @HandleGet("/todos/overdue")
    fun getOverdueTodos(page: Int = 1, pageSize: Int = 20): List<TodoItem> {
        return Fluxzero.queryAndWait(GetOverdueTodos(page, pageSize))
    }

    @HandlePut("/todos/{todoId}/complete")
    fun completeTodo(todoId: String): TodoItem {
        return Fluxzero.sendCommandAndWait(CompleteTodo(TodoId(todoId)))
    }

    @HandlePut("/todos/{todoId}/uncomplete")
    fun uncompleteTodo(todoId: String): TodoItem {
        return Fluxzero.sendCommandAndWait(UncompleteTodo(TodoId(todoId)))
    }

    @HandlePut("/todos/{todoId}/rename")
    fun renameTodo(todoId: String, title: String, description: String? = null): TodoItem {
        return Fluxzero.sendCommandAndWait(RenameTodo(TodoId(todoId), title, description))
    }

    @HandlePut("/todos/{todoId}/assign")
    fun assignTodo(todoId: String, assignee: String?): TodoItem {
        return Fluxzero.sendCommandAndWait(AssignTodo(TodoId(todoId), assignee))
    }

    @HandlePut("/todos/{todoId}/due-date")
    fun setDueDate(todoId: String, dueDate: LocalDate?): TodoItem {
        return Fluxzero.sendCommandAndWait(SetDueDate(TodoId(todoId), dueDate))
    }

    @HandleDelete("/todos/{todoId}")
    fun removeTodo(todoId: String) {
        Fluxzero.sendCommandAndWait<TodoItem?>(RemoveTodo(TodoId(todoId)))
    }
}