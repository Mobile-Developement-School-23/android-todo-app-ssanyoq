package com.example.yatodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.recycler.TodoItemAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todoItemsRepository = TodoItemsRepository()
        todoItemsRepository.generate()

        val todoItemsRecyclerView = findViewById<RecyclerView>(R.id.todo_items)
        val todoItemAdapter = TodoItemAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        todoItemsRecyclerView.adapter = todoItemAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemAdapter.todoItems = todoItemsRepository.getTodoItems()

        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)
        val motionLayout = findViewById<MotionLayout>(R.id.toolbar)
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }

        // New item button handling
        val newItemButton = findViewById<FloatingActionButton>(R.id.new_item_button)
        newItemButton.setOnClickListener {
            openTaskMenuActivity()
        }

        // Visibility button handling
        var isVisibilityTurnedOn = true
        val visibilityButton = findViewById<ShapeableImageView>(R.id.visibilityIcon)
        visibilityButton.setOnClickListener {
            if (isVisibilityTurnedOn) {
                visibilityButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.visibility_off
                    )
                )
            } else {
                visibilityButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.visibility
                    )
                )
            }
            isVisibilityTurnedOn = !isVisibilityTurnedOn

        }
    }

    fun openTaskMenuActivity() {
        val intent = Intent(this, TaskMenuActivity::class.java)
        startActivity(intent)
    }
}