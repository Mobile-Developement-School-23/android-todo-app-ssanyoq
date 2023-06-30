package com.example.yatodo.data

import android.icu.util.Calendar
import kotlinx.coroutines.delay

class NetworkDataResource {
    val calendar = Calendar.getInstance()
    private val data = listOf(
        TodoItem(
            taskId = "aaa",
            text = "Some text but completed", isCompleted = true,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        ), TodoItem(
            taskId = "bbb",
            text = "Some text but with no deadline date", isCompleted = true,
            createdAt = calendar.time, deadline = null,
            importance = Importance.HIGH, modifiedAt = null
        ), TodoItem(
            taskId = "c",
            text = "Some text but with \nthese \nthings", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        ), TodoItem(
            taskId = "ddd",
            text = "Some really really really really really really really really really really really really really really really really really really long text",
            isCompleted = false,
            createdAt = calendar.time,
            deadline = calendar.time,
            importance = Importance.HIGH,
            modifiedAt = null
        ), TodoItem(
            taskId = "eee",
            text = "Please", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        ), TodoItem(
            taskId = "fff",
            text = "It's 3AM", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        ), TodoItem(
            taskId = "ggg",
            text = "Help me", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.HIGH, modifiedAt = null
        )
    )

    suspend fun loadTasks():List<TodoItem> {
        delay(10)
        return data
    }
}