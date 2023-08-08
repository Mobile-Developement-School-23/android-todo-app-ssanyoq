package com.example.yatodo.components

import com.example.yatodo.data.TodoItemsRepository
import com.example.yatodo.viewmodel.ViewModelFactory

class ApplicationComponent {
    private val todoItemsRepository = TodoItemsRepository()

    /**
     * Public getter allows all other classes to use same instance from [ApplicationComponent].
     */
    val viewModelFactory = ViewModelFactory(todoItemsRepository)
}
