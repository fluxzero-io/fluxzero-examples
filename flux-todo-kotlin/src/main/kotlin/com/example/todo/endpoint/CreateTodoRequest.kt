package com.example.todo.endpoint

import java.time.LocalDate

data class CreateTodoRequest(
    val title: String,
    val description: String? = null,
    val assignee: String? = null,
    val dueDate: LocalDate? = null
)