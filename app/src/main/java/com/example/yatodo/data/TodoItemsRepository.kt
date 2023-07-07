package com.example.yatodo.data

import android.icu.util.Calendar
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TodoItemsRepository(private val dataSource: NetworkDataResource)
{
    private var _todoItemsList = MutableLiveData<List<TodoItem>>(emptyList())

    val todoItemsList: LiveData<List<TodoItem>> = _todoItemsList


    fun getItemById(id: String?): TodoItem? {
        if (id == null) {
            return null
        }
        for (i in 0 until (todoItemsList.value?.size!!)) {
            if (todoItemsList.value?.get(i)?.taskId == id) {
                return todoItemsList.value?.get(i)
            }
        }
        return null
    }

    /**
     * Changes value of `isChecked` of `TodoItem` with id [taskId] to [isChecked].
     * Doesn't do anything if there is no item with such id
     */
    suspend fun checkItemById(taskId: String, isChecked: Boolean) {
        val newItems = withContext(Dispatchers.Default) {
            todoItemsList.value.orEmpty()
                .map { item -> if (item.taskId == taskId) item.copy(isCompleted = isChecked) else item }
        }
        _todoItemsList.value = newItems
    }

    suspend fun changeItemById(taskId: String, newItem: TodoItem) {
        val newItems = withContext(Dispatchers.Default) {
            newItem.taskId = taskId // just in case
            todoItemsList.value.orEmpty()
                .map {item -> if (item.taskId == taskId) newItem else item}
        }
        _todoItemsList.value = newItems
    }


    /**
     * Deletes `TodoItem` with given [taskId]. Nothing will change if there is no item with such id
     */
    suspend fun deleteItemById(taskId: String) {
        val newItems = withContext(Dispatchers.Default) {
            todoItemsList.value.orEmpty().filter { it -> it.taskId != taskId }
        }
        _todoItemsList.value = newItems
    }

    /**
    * Adds [todoItem] and generates `taskId` for it (equals `size.toString()`)
     */
    suspend fun addItem(todoItem: TodoItem?) {
        if (todoItem == null) {
            return
        }
        val newItems = withContext(Dispatchers.Default) {
            todoItem.taskId = todoItemsList.value.orEmpty().size.toString()
            todoItemsList.value.orEmpty().plus(todoItem)
        }
        _todoItemsList.value = newItems
    }
    @MainThread
    suspend fun countDone(): Int {
        val doneAmount = withContext(Dispatchers.Default) {
            todoItemsList.value?.count { item -> item.isCompleted }!!
        }
        return doneAmount
    }

    @MainThread
    suspend fun updateTodoItems() {
        val loadedData = withContext(Dispatchers.IO) {dataSource.loadTasks()}
        _todoItemsList.value = loadedData
    }



    suspend fun generate() {
        val calendar = Calendar.getInstance()
        val i1 = TodoItem(
            taskId = "aaa",
            text = "Some text but completed", isCompleted = true,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        )
        val i2 = TodoItem(
            taskId = "bbb",
            text = "Some text but with no deadline date", isCompleted = true,
            createdAt = calendar.time, deadline = null,
            importance = Importance.HIGH, modifiedAt = null
        )
        val i3 = TodoItem(
            taskId = "c",
            text = "Some text but with \nthese \nthings", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        )
        val i4 = TodoItem(
            taskId = "ddd",
            text = "Some really really really really really really really really really really really really really really really really really really long text",
            isCompleted = false,
            createdAt = calendar.time,
            deadline = calendar.time,
            importance = Importance.HIGH,
            modifiedAt = null
        )
        val i5 = TodoItem(
            taskId = "eee",
            text = "Please", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.LOW, modifiedAt = null
        )
        val i6 = TodoItem(
            taskId = "fff",
            text = "It's 3AM", isCompleted = false,
            createdAt = calendar.time, deadline = calendar.time,
            importance = Importance.COMMON, modifiedAt = null
        )
        val i7 = TodoItem(
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