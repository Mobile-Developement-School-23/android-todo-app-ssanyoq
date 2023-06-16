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
        this.todoItemsList.removeIf { item: TodoItem -> item.taskId == id }
    }

    fun countDone(): Int {
        return this.todoItemsList.count { item -> item.isCompleted }
    }
    fun generate() {
        val i1: TodoItem = TodoItem(
            taskId = "aaa",
            taskText = "Please buy milk \nI beg you\nOr else...1", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )

        val i2: TodoItem = TodoItem(
            taskId = "bbb",
            taskText = "Please buy milk \nI beg you\nOr else...2", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i3: TodoItem = TodoItem(
            taskId = "ccc",
            taskText = "Please buy milk \nI beg you\nOr else...3", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i4: TodoItem = TodoItem(
            taskId = "ddd",
            taskText = "Please buy milk \nI beg you\nOr else...4", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i5: TodoItem = TodoItem(
            taskId = "eee",
            taskText = "Please buy milk \nI beg you\nOr else...5", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i6: TodoItem = TodoItem(
            taskId = "fff",
            taskText = "Please buy milk \nI beg you\nOr else...6", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i7: TodoItem = TodoItem(
            taskId = "ggg",
            taskText = "Please buy milk \nI beg you\nOr else...7", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        val i8: TodoItem = TodoItem(
            taskId = "eee",
            taskText = "Please buy milk \nI beg you\nOr else...8", isCompleted = false,
            creationDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH, changeDate = null
        )
        this.addItem(i1)
        this.addItem(i2)
        this.addItem(i3)
        this.addItem(i4)
        this.addItem(i5)
        this.addItem(i6)
        this.addItem(i7)
        this.addItem(i8)
        this.addItem(i8)
        this.addItem(i8)
        this.addItem(i8)
        this.addItem(i8)
    }
}