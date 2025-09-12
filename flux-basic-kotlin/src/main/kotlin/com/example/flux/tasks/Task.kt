package com.example.flux.tasks

import io.fluxzero.sdk.modeling.Aggregate
import io.fluxzero.sdk.modeling.EntityId

@Aggregate(searchable = true)
data class Task(
    @EntityId
    val taskId: TaskId,
    val title: String,
    val description: String,
)
