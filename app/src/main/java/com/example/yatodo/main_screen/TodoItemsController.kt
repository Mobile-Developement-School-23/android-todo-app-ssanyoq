package com.example.yatodo.main_screen

import android.app.Activity
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.R
import com.example.yatodo.recycler.TodoItemAdapter
import com.example.yatodo.viewmodel.TodoItemsViewModel

class TodoItemsController(
    private val activity: Activity,
    rootView: View,
    private val adapter: TodoItemAdapter,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: TodoItemsViewModel,
) {
    private val recyclerView = rootView.findViewById<RecyclerView>(R.id.todo_items)

    fun setUpTodoItemsList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        viewModel.todoItems.observe(lifecycleOwner) {newItems ->
            adapter.submitList(newItems)
            // TODO isrefreshing
        }
    }

    fun setUpViews() {
        // TODO swipe to refresh
        setUpTodoItemsList()
    }

}