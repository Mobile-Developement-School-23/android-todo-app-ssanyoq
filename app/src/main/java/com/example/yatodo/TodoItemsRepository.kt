package com.example.yatodo

import android.icu.util.Calendar
import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import java.time.LocalDate


class TodoItemsRepository {
    companion object {
        var todoItemsList = mutableListOf<TodoItem>()
        var indexToChange = -1
    }

    private operator fun set(i: Int, value: TodoItem) {
        todoItemsList[i] = value
    }

    operator fun get(i: Int): TodoItem {
        return todoItemsList[i]
    }

    fun set(list: MutableList<TodoItem>) {
        todoItemsList = list
    }

    fun getTodoItems(): MutableList<TodoItem> {
        return todoItemsList
    }

    fun addItem(newItem: TodoItem) {
        todoItemsList.add(newItem)
    }

    fun removeItemById(id: String) {
        todoItemsList.removeIf { item: TodoItem -> item.taskId == id }
    }

    fun countDone(): Int {
        return todoItemsList.count { item -> item.isCompleted }
    }

    fun generate() {
        val calendar = Calendar.getInstance()
        val i1: TodoItem = TodoItem(
            taskId = "aaa",
            text = "Some text but completed", isCompleted = true,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        )
        val i2: TodoItem = TodoItem(
            taskId = "bbb",
            text = "Some text but with no deadline date", isCompleted = false,
            createdAt = calendar.time, deadline = null,
            importance = Importance.HIGH, modifiedAt = null
        )
        val i3: TodoItem = TodoItem(
            taskId = "c",
            text = "Some text but with \nthese \nthings", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        )
        val i4: TodoItem = TodoItem(
            taskId = "ddd",
            text = "Some really really really really really really really really really really really really really really really really really really long text",
            isCompleted = false,
            createdAt = calendar.time,
            deadline = calendar.time,
            importance = Importance.HIGH,
            modifiedAt = null
        )
        val i5: TodoItem = TodoItem(
            taskId = "eee",
            text = "Please", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        )
        val i6: TodoItem = TodoItem(
            taskId = "fff",
            text = "It's 3AM", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        )
        val i7: TodoItem = TodoItem(
            taskId = "ggg",
            text = "Help me", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.HIGH, modifiedAt = null
        )
        addItem(i1)
        addItem(i2)
        addItem(i3)
        addItem(i4)
        addItem(i5)
        addItem(i6)
        addItem(i7)
    }
}