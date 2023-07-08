package com.example.yatodo.domain

import androidx.recyclerview.widget.DiffUtil
import com.example.yatodo.data.TodoItem

/**
 * Diff calculator for `TodoItem`s in Recycler view
 */
class TodoItemDiffCalculator: DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(
        oldItem: TodoItem,
        newItem: TodoItem
    ): Boolean {
        return oldItem.taskId == newItem.taskId
    }
    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}
