package com.example.yatodo.components

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import java.util.Date

/**
 * Stores inputted data and `todoItem` from `arguments`. Also has `composeTodoItem` function that
 * composes `TodoItem` from data stored in this component
 */
class TaskFragmentComponent(arguments: Bundle?) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val todoItem: TodoItem? = arguments?.getParcelable("todo_item", TodoItem::class.java)
    var pickedDate: Date? = null
    var taskText: String? = null
    var importance: Importance = Importance.COMMON

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
