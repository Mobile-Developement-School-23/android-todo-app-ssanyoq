package com.example.yatodo.data

import java.util.Date

enum class Importance {
    LOW, COMMON, HIGH
}

data class TodoItem(
    var taskId: String,
    var text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    var createdAt: Date,
    var modifiedAt: Date?,
)