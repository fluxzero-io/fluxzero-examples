package com.example.flux.tasks

import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.HandleCommand

data class AddTask(
    val taskId: TaskId,
    val title: String,
    val description: String,
) {
    @HandleCommand
    fun handle(): Task = Fluxzero.loadAggregate(taskId).assertAndApply(this).get()

    @Apply
    fun apply() = Task(taskId, title, description)
}
