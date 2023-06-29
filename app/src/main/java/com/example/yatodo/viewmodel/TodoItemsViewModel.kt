package com.example.yatodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatodo.data.TodoItem
import com.example.yatodo.data.TodoItemsRepository
import kotlinx.coroutines.launch

class TodoItemsViewModel (private val todoItemsRepository: TodoItemsRepository): ViewModel() {
    val todoItems = todoItemsRepository.todoItemsList

    init {
        updateTodoItems()
    }

    fun updateTodoItems() {
        viewModelScope.launch {
            todoItemsRepository.updateTodoItems()
        }
    }
    fun onItemOpened(todoItem: TodoItem) {
        // TODO
    }
    fun onItemChecked(todoItem: TodoItem, isChecked: Boolean) {
        viewModelScope.launch {
            todoItemsRepository.checkItemById(todoItem.taskId, isChecked)
        }
    }
}