package com.example.yatodo.viewmodel

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.yatodo.R
import com.example.yatodo.data.TodoItem
import com.example.yatodo.data.TodoItemsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for TodoItems related tasks
 */
class TodoItemsViewModel @Inject constructor(private val todoItemsRepository: TodoItemsRepository) :
    ViewModel() {
    val todoItems = todoItemsRepository.todoItemsList

    init {
        updateTodoItems()
    }
    fun updateTodoItems() {
        viewModelScope.launch {
            todoItemsRepository.updateTodoItems()
        }
    }

    /**
     * Function that opens TaskFragment and passes [todoItem] for it. Takes [view] for
     * navigation purposes
     *
     * Usually called by pressing info buttons in recycler view
     */
    fun onItemOpened(todoItem: TodoItem, view: View) {
        Navigation.findNavController(view).navigate(
            R.id.action_main_to_task_fragment, bundleOf(
                "todo_item" to todoItem
            )
        )
    }

    /**
     * Changes [todoItem]`.isChecked` to [isChecked] and repaints necessary elements
     */
    fun onItemChecked(todoItem: TodoItem, isChecked: Boolean) {
        viewModelScope.launch {
            todoItemsRepository.checkItemById(todoItem.taskId, isChecked)
        }
    }

    /**
     * Null-safe `coundDone()`
     */
    suspend fun countDone(): Int {
        return if (todoItemsRepository.todoItemsList.value?.size == 0) {
            0
        } else {
            todoItemsRepository.countDone()
        }
    }

    /**
     * Calls `todoItemsRepository` `addItem` method for [todoItem]
     */
    suspend fun addItem(todoItem: TodoItem?) {
        todoItemsRepository.addItem(todoItem)
    }

    /**
     * Calls `todoItemsRepository` `changeItemById` method for [todoItem]
     */
    suspend fun changeItem(todoItem: TodoItem) {
        todoItemsRepository.changeItemById(todoItem.taskId, todoItem)
    }

    /**
     * Calls `todoItemsRepository` `deleteItem` method for [todoItem]
     */
    suspend fun deleteItem(todoItem: TodoItem) {
        todoItemsRepository.deleteItemById(todoItem.taskId)
    }
}
