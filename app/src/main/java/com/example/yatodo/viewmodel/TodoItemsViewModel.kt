package com.example.yatodo.viewmodel

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.yatodo.R
import com.example.yatodo.data.InteractionType
import com.example.yatodo.data.TodoItem
import com.example.yatodo.data.TodoItemsRepository
import kotlinx.coroutines.launch

class TodoItemsViewModel(private val todoItemsRepository: TodoItemsRepository) : ViewModel() {
    val todoItems = todoItemsRepository.todoItemsList

    init {
        updateTodoItems()
    }

    fun updateTodoItems() {
        viewModelScope.launch {
            todoItemsRepository.updateTodoItems()
        }
    }

    fun onItemOpened(todoItem: TodoItem, view: View) {
        Navigation.findNavController(view).navigate(
            R.id.action_main_to_task_fragment, bundleOf(
                "todo_item" to todoItem
            )
        )
    }

    fun onItemChecked(todoItem: TodoItem, isChecked: Boolean) {
        viewModelScope.launch {
            todoItemsRepository.checkItemById(todoItem.taskId, isChecked)
        }
    }

    suspend fun countDone(): Int {
        if (todoItemsRepository.todoItemsList.value?.size == 0) {
            return 0
        } else {
            return todoItemsRepository.countDone()
        }
    }

    suspend fun addItem(todoItem: TodoItem?) {
        todoItemsRepository.addItem(todoItem)
    }

    suspend fun changeItem(todoItem: TodoItem) {
        todoItemsRepository.changeItemById(todoItem.taskId, todoItem)
    }

    suspend fun deleteItem(todoItem: TodoItem) {
        todoItemsRepository.deleteItemById(todoItem.taskId)
    }
}