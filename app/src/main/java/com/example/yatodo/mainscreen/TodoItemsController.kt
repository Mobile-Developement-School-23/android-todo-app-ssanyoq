package com.example.yatodo.mainscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.R
import com.example.yatodo.recycler.TodoItemAdapter
import com.example.yatodo.viewmodel.TodoItemsViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoItemsController @Inject constructor(
    private val activity: Activity,
    rootView: View,
    private val adapter: TodoItemAdapter,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: TodoItemsViewModel,
) {
    private val recyclerView = rootView.findViewById<RecyclerView>(R.id.todo_items)
    private val statusLabel = rootView.findViewById<TextView>(R.id.helper_text)
    /**
     * sets up list of items in Recycler view
     */
    private fun setUpTodoItemsList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        viewModel.todoItems.observe(lifecycleOwner) {newItems ->
            adapter.submitList(newItems)
        }
    }

    /**
     * Changes Done counter on `viewModel.todoItems` change
     */
    private fun setUpDoneItemsCounter(doneString: String) {
        setString(doneString)
        viewModel.todoItems.observe(lifecycleOwner) {setString(doneString)}
    }

    @SuppressLint("SetTextI18n")
    private fun setString(doneString: String) {
        viewModel.viewModelScope.launch {
            statusLabel.text =
                doneString + " " + viewModel.countDone().toString()
        }
    }

    /**
     * sets up all necessary views with some logic
     */
    fun setUpViews(doneString: String) {
        setUpTodoItemsList()
        setUpDoneItemsCounter(doneString)
    }

}
