package com.example.yatodo.data

import java.time.LocalDate

enum class Importance {
    LOW, MEDIUM, HIGH
}

data class TodoItem(
    var taskId: String,
    var taskText: String,
    var importance: Importance,
    var deadlineDate: LocalDate?,
    var isCompleted: Boolean,
    var creationDate: LocalDate,
    var changeDate: LocalDate?,
)