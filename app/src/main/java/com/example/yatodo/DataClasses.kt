package com.example.yatodo

import java.time.LocalDate
import java.util.function.Predicate

enum class Importance {
    LOW, MEDIUM, HIGH
}

data class TodoItem (
    var taskId: String,
    var taskText: String,
    var importance: Importance,
    var withDeadline: Boolean, // TODO
    var deadlineDate: LocalDate,
    var isCompleted: Boolean,
    var creationDate: LocalDate,
    var changeDate: LocalDate, // TODO
)

class TodoItemsRepository {
    var todoItemsList : MutableList<TodoItem> = mutableListOf()

    fun addItem(newItem: TodoItem) {
        this.todoItemsList.add(newItem)
    }
    fun removeItemById(id: String) {
        this.todoItemsList.removeIf(Predicate { item : TodoItem -> item.taskId == id})
    }
    fun generate() {
        val i1:TodoItem = TodoItem(taskId = "aaa",
            taskText = "Please buy milk I beg you", isCompleted = false,
            withDeadline = false, creationDate = LocalDate.now(),
            changeDate = LocalDate.now(), deadlineDate = LocalDate.now(),
            importance = Importance.HIGH)
        val i2:TodoItem
        val i3:TodoItem
        val i4:TodoItem
        val i5:TodoItem // TODO
        val i6:TodoItem
        val i7:TodoItem
        this.addItem(i1)

    }
}