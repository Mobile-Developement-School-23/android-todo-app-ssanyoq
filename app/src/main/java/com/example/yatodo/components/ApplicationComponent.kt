package com.example.yatodo.components

import android.app.Application
import com.example.yatodo.data.TodoItemsRepository
import com.example.yatodo.database.TodoDatabase
import com.example.yatodo.viewmodel.ViewModelFactory

class ApplicationComponent(application: Application) {
    val database = TodoDatabase.getDatabase(application.applicationContext).todoDao()
    private val todoItemsRepository = TodoItemsRepository(database)

    /**
     * Public getter allows all other classes to use same instance from [ApplicationComponent].
     */
    val viewModelFactory = ViewModelFactory(todoItemsRepository)
}
