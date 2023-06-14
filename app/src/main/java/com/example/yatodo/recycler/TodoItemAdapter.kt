package com.example.yatodo.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.yatodo.R
import com.example.yatodo.data.TodoItem

class TodoItemAdapter : RecyclerView.Adapter<ViewHolder>() {
    var todoItems = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TODO_ITEM_TYPE -> TodoItemViewHolder(
                layoutInflater.inflate(
                    R.layout.todo_item,
                    parent,
                    false
                )
            )
            ADD_ITEM_TYPE -> AddItemViewHolder(
                layoutInflater.inflate(
                    R.layout.add_item,
                    parent,
                    false
                )
            )
            else -> throw java.lang.IllegalArgumentException("Invalid ViewHolder type")
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == todoItems.size) {
            ADD_ITEM_TYPE
        } else {
            TODO_ITEM_TYPE
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TodoItemViewHolder -> holder.onBind(todoItems[position] as TodoItem)
//            is AddItemViewHolder -> //nothing changes
        }
    }
    companion object {
        private const val TODO_ITEM_TYPE = 0
        private const val ADD_ITEM_TYPE = 1
    }
}