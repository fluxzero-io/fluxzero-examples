package com.example.todo.api

import com.example.todo.domain.TodoItem
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.tracking.TrackSelf
import io.fluxzero.sdk.tracking.handling.HandleCommand

@TrackSelf
interface TodoCommand {
    val todoId: TodoId

    @HandleCommand
    fun handle(): TodoItem? {
        return Fluxzero.loadAggregate<TodoItem>(todoId).assertAndApply(this).get()
    }
}