package com.example.yatodo.components

import android.os.Bundle
import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import java.util.Date

class TaskFragmentComponent(arguments: Bundle?) {
    val todoItem: TodoItem? = arguments?.getParcelable("todo_item")
    var pickedDate: Date? = null
    var taskText: String? = null
    var importance: Importance = Importance.COMMON

    fun composeTodoItem(): TodoItem? {
        if (taskText == null) {
            return null
        } else {
            var createdAt = Date(System.currentTimeMillis())
            var modifiedAt: Date? = null
            var isCompleted = false
            if (todoItem != null) { // Only if called from change item button
                modifiedAt = createdAt
                createdAt = todoItem.createdAt
                isCompleted = todoItem.isCompleted

            }
            return TodoItem(
                "To be changed", // definitely will be changed
                taskText!!,
                importance,
                pickedDate,
                isCompleted,
                createdAt,
                modifiedAt
            )
        }
    }
}