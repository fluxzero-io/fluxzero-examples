package com.example.flux.tasks

import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.tracking.handling.HandleQuery

data class GetTask(
    val id: TaskId,
) {
    @HandleQuery
    fun handle(): Task = Fluxzero.loadAggregate(id).get()
}
