package com.example.yatodo.main_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.R
import com.example.yatodo.TaskFragment
import com.example.yatodo.TodoItemsRepository
import com.example.yatodo.recycler.TodoItemAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

class FragmentMain() : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoItemsRepository = TodoItemsRepository()
        todoItemsRepository.generate()

        val todoItemsRecyclerView = view.findViewById<RecyclerView>(R.id.todo_items)
        val todoItemAdapter = TodoItemAdapter()
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        todoItemsRecyclerView.adapter = todoItemAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemAdapter.todoItems = todoItemsRepository.getTodoItems()

        val statusLabel = view.findViewById<TextView>(R.id.helper_text)
        statusLabel.text = getString(R.string.tasks_done, todoItemsRepository.countDone())

        val appBarLayout = view.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val motionLayout = view.findViewById<MotionLayout>(R.id.toolbar)
        appBarLayout?.addOnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            if (motionLayout != null) {
                motionLayout.progress = seekPosition
            }
        }


        // New item button handling
        val newItemButton = view.findViewById<FloatingActionButton>(R.id.new_item_button)
        newItemButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_main_to_task_fragment)
        }

        // Visibility button handling
        var isVisibilityTurnedOn = true
        val visibilityButton = view.findViewById<ShapeableImageView>(R.id.visibilityIcon)
        visibilityButton.setOnClickListener {
            if (isVisibilityTurnedOn) {
                visibilityButton.setImageDrawable(
                    this.context?.let { it1 ->
                        AppCompatResources.getDrawable(
                            it1,
                            R.drawable.visibility_off
                        )
                    }
                )
            } else {
                visibilityButton.setImageDrawable(
                    this.context?.let { it1 ->
                        AppCompatResources.getDrawable(
                            it1,
                            R.drawable.visibility
                        )
                    }
                )
            }
            isVisibilityTurnedOn = !isVisibilityTurnedOn

        }
    }

    fun onCheckBoxClicked(id: String, repository: TodoItemsRepository) {
    }
}