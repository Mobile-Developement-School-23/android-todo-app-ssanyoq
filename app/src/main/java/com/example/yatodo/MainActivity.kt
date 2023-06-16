package com.example.yatodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.recycler.TodoItemAdapter
import com.google.android.material.appbar.AppBarLayout
import com.example.yatodo.TaskMenuActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

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

        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)
        val motionLayout = findViewById<MotionLayout>(R.id.toolbar)
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }

        val btn = findViewById<ShapeableImageView>(R.id.visibilityIcon)
        btn.setOnClickListener {
            openTaskMenuActivity()
        }
    }
    fun openTaskMenuActivity() {
        val intent = Intent(this, TaskMenuActivity::class.java)
        startActivity(intent)
    }
}