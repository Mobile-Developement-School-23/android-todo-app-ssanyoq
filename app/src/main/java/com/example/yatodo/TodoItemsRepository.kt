package com.example.yatodo

import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import java.time.LocalDate
import java.util.function.Predicate


class TodoItemsRepository {
    var todoItemsList: MutableList<TodoItem> = mutableListOf()
    private operator fun set(i: Int, value: TodoItem) {
        todoItemsList[i] = value
    }

    operator fun get(i: Int): TodoItem {
        return todoItemsList[i]
    }

    fun getTodoItems(): MutableList<TodoItem> {
        return this.todoItemsList
    }

    fun addItem(newItem: TodoItem) {
        this.todoItemsList.add(newItem)
    }

    fun removeItemById(id: String) {
        this.todoItemsList.removeIf(Predicate { item: TodoItem -> item.taskId == id })
    }

    fun generate() {
        val i1: TodoItem = TodoItem(
            taskId = "aaa",
            taskText = "Please buy milk \nI beg you\nOr else...", isCompleted = false,
            withDeadline = false, creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i2: TodoItem
        val i3: TodoItem
        val i4: TodoItem
        val i5: TodoItem // TODO
        val i6: TodoItem
        val i7: TodoItem
        this.addItem(i1)

    }
}