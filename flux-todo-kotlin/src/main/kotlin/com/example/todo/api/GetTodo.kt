package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.tracking.handling.HandleQuery
import io.fluxzero.sdk.tracking.handling.Request

data class GetTodo(
    val todoId: TodoId
) : Request<TodoItem?> {

    @HandleQuery
    fun handle(): TodoItem? {
        return Fluxzero.loadAggregate<TodoItem>(todoId).get()
    }
}