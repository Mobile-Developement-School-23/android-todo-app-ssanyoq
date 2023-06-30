package com.example.yatodo.components

import com.example.yatodo.data.NetworkDataResource
import com.example.yatodo.data.TodoItemsRepository
import com.example.yatodo.viewmodel.ViewModelFactory

class ApplicationComponent {
    private val dataSource = NetworkDataResource()
    private val todoItemsRepository = TodoItemsRepository(dataSource)

    /**
     * Public getter allows all other classes to use same instance from [ApplicationComponent].
     */
    val viewModelFactory = ViewModelFactory(todoItemsRepository)
}