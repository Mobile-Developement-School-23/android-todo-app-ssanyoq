package com.example.yatodo.recycler

import android.content.res.ColorStateList
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.R
import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import com.example.yatodo.viewmodel.TodoItemsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class TodoItemViewHolder(
    itemView: View,
    private val viewModel: TodoItemsViewModel
) : RecyclerView.ViewHolder(itemView) {
    private val taskDescription: TextView = itemView.findViewById(R.id.task_description)
    private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    private val importanceIndicator: ImageView =
        itemView.findViewById(R.id.importance_indicator)
    private val deadLineDate: TextView = itemView.findViewById(R.id.deadline_date)
    private val infoButton: ImageButton = itemView.findViewById(R.id.info_button)

    fun onBind(todoItem: TodoItem) {
        Log.d("view-holder", "---------------")
        Log.d("view-holder", "New item: ")
        Log.d("view-holder", "importance: " + todoItem.importance.toString())
        Log.d("view-holder", "is completed: " + todoItem.isCompleted.toString())
        Log.d("view-holder", "id: " + todoItem.taskId)
        Log.d("view-holder", "name: " + todoItem.text)
        Log.d("view-holder", "---------------")
        when (todoItem.importance) {
            Importance.LOW -> {
                importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.arrow_down
                    )
                )
            }

            Importance.COMMON -> {
                // No drawables, just empty space
                importanceIndicator.setImageDrawable(null)
            }

            Importance.HIGH -> {
                importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.double_exclamation
                    )
                )
                checkBox.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.red
                        )
                    )
            }
        }
        checkBox.setOnClickListener {
            viewModel.onItemChecked(todoItem, checkBox.isChecked)
        }
        infoButton.setOnClickListener {
            viewModel.onItemOpened(todoItem, itemView)
        }
        taskDescription.text = todoItem.text
        checkBox.isChecked = todoItem.isCompleted

        if (checkBox.isChecked) {
            taskDescription.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            taskDescription.paintFlags = 0
        }

        if (todoItem.deadline == null) {
            deadLineDate.visibility = View.GONE
        } else {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            deadLineDate.text = sdf.format(todoItem.deadline!!)
        }
    }
}