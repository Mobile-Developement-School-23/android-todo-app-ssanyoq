package com.example.yatodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodoItemAdapter: RecyclerView.Adapter<TodoItemViewHolder>() {
    var todoItems = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(
            layoutInflater.inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(todoItems[position])
    }
}