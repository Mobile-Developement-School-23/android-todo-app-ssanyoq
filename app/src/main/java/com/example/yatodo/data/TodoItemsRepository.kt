package com.example.yatodo.data

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yatodo.database.TodoDao
import com.example.yatodo.network.TodoApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository of [TodoItem]s. When initialized tries to get data from [].
 * TodoItems are stored in `LiveData`
 *
 * Most operations are done in background threads
 */
@Singleton
class TodoItemsRepository @Inject constructor(private val dao: TodoDao) {
    private var _todoItemsList = MutableLiveData<List<TodoItem>>(emptyList())

    val todoItemsList: LiveData<List<TodoItem>> = _todoItemsList

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
        withContext(Dispatchers.IO) {
            val curItem = dao.getItem(taskId)
            curItem.isCompleted = !curItem.isCompleted
            dao.updateItem(curItem)
        }
        withContext(Dispatchers.IO) {
            TodoApiImpl.checkItemById(taskId)
        }

    }

    /**
     * Changes `TodoItem` from repo with [taskId] to a new [newItem]. [newItem]'s
     * `taskId` is changed to a new one just in case
     */
    suspend fun changeItemById(taskId: String, newItem: TodoItem) {
        val newItems = withContext(Dispatchers.Default) {
            newItem.taskId = taskId // just in case
            todoItemsList.value.orEmpty()
                .map { item -> if (item.taskId == taskId) newItem else item }
        }
        _todoItemsList.value = newItems
        withContext(Dispatchers.IO) {
            newItem.taskId = taskId
            dao.insert(newItem)
        }
        withContext(Dispatchers.IO) {
            newItem.taskId = taskId // yet again
            TodoApiImpl.updateItem(newItem)
        }
    }


    /**
     * Deletes `TodoItem` with given [taskId]. Nothing will be
     * changed if there is no item with such id
     */
    suspend fun deleteItemById(taskId: String) {
        val newItems = withContext(Dispatchers.Default) {
            todoItemsList.value.orEmpty().filter { it.taskId != taskId }
        }
        _todoItemsList.value = newItems
        withContext(Dispatchers.IO) {
            val curItem = dao.getItem(taskId)
            dao.deleteItem(curItem)
        }
        withContext(Dispatchers.IO) {
            TodoApiImpl.deleteItem(taskId)
        }

    }

    /**
     * Adds [todoItem] and generates `taskId` for it (equals `size.toString()`)
     */
    suspend fun addItem(todoItem: TodoItem) {
        todoItem.taskId = todoItemsList.value.orEmpty().size.toString()
        val newItems = withContext(Dispatchers.Default) {
            todoItemsList.value.orEmpty().plus(todoItem)
        }

        _todoItemsList.value = newItems
        withContext(Dispatchers.IO) {
            dao.insert(todoItem)
        }
        withContext(Dispatchers.IO) {
            TodoApiImpl.addItem(todoItem)
        }
    }

    /**
     * Counts how many `TodoItem`s have `isCompleted == true`
     */
    @MainThread
    suspend fun countDone(): Int {
        val doneAmount = withContext(Dispatchers.Default) {
            todoItemsList.value?.count { item -> item.isCompleted }!!
        }
        return doneAmount
    }

    @MainThread
    suspend fun updateTodoItems() {
        val loadedData = withContext(Dispatchers.IO) {
//            TodoApiImpl.getList()
            dao.getItems()
        }
        _todoItemsList.value = loadedData
    }
}
