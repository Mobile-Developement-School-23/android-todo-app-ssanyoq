package com.example.yatodo.components

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.yatodo.data.Importance
import com.example.yatodo.data.InteractionType
import com.example.yatodo.data.TodoItem
import com.example.yatodo.taskscreen.TaskFragmentArgs
import java.util.Date

/**
 * Stores inputted data and `todoItem` from `arguments`. Also has `composeTodoItem` function that
 * composes `TodoItem` from data stored in this component
 */
class TaskFragmentComponent(arguments: TaskFragmentArgs?) {
    val todoItem: TodoItem? = arguments?.todoItem
//    val todoItem: TodoItem? = arguments?.getParcelable<TodoItem>("todo_item")
    var pickedDate: Date? = null
    var taskText: String? = null
    var importance: Importance = Importance.COMMON

    fun composeTodoItem(returnOld: Boolean = false): TodoItem? {
        if (returnOld) {
            return todoItem
        }
        if (taskText == null) {
            return null
        } else {
            var createdAt = Date(System.currentTimeMillis())
            var modifiedAt: Date? = null
            var isCompleted = false
            var taskId = "To be changed"
            if (todoItem != null) { // Only if called from change item button
                modifiedAt = createdAt
                createdAt = todoItem.createdAt
                isCompleted = todoItem.isCompleted
                taskId = todoItem.taskId
            }
            return TodoItem(
                taskId, // definitely will be changed
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
