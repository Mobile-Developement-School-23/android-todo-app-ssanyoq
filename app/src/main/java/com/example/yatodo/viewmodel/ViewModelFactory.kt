package com.example.yatodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yatodo.data.TodoItemsRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val todoItemsRepository: TodoItemsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        TodoItemsViewModel::class.java -> TodoItemsViewModel(
            todoItemsRepository,
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}