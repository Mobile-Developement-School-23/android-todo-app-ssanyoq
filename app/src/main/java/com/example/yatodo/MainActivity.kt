package com.example.yatodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date
import com.example.yatodo.TodoItem
import com.example.yatodo.Importance
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var todoItemsRepository = TodoItemsRepository()
        todoItemsRepository.generate()

        val todoItemsRecyclerView = findViewById<RecyclerView>(R.id.todo_items)
        val todoItemAdapter = TodoItemAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        todoItemsRecyclerView.adapter = todoItemAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemAdapter.todoItems = todoItemsRepository.getTodoItems() // (this)?
    }
}