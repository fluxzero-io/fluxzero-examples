package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.tracking.handling.HandleQuery
import io.fluxzero.sdk.tracking.handling.Request

data class ListTodos(
    val page: Int = 1,
    val pageSize: Int = 20
) : Request<List<TodoItem>> {

    @HandleQuery
    fun handle(): List<TodoItem> {
        // In a real implementation, this would query a search index
        // For this example, we return an empty list
        // Proper search requires indexing configuration
        return emptyList()
    }
}