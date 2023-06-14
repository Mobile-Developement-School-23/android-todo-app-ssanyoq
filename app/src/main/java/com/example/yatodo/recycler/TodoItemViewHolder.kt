package com.example.yatodo.recycler

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yatodo.data.Importance
import com.example.yatodo.R
import com.example.yatodo.data.TodoItem

class TodoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val taskDescription: TextView = itemView.findViewById(R.id.task_description)
    private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    private val importanceIndicator: ImageView = itemView.findViewById(R.id.importance_indicator)
    private val deadLineDate: TextView = itemView.findViewById(R.id.deadline_date)

    fun onBind(todoItem: TodoItem) {
        taskDescription.text = todoItem.taskText // TODO text cutting
        checkBox.isChecked = todoItem.isCompleted
        deadLineDate.text = todoItem.deadlineDate.toString() // TODO
        when (todoItem.importance) {
            Importance.LOW -> {
                importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.arrow_down
                    )
                )
            }
            Importance.MEDIUM -> {
                // No drawables, just empty space
            }
            Importance.HIGH -> {
                importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.double_exclamation
                    )
                )
            }
        }
    }
}

class AddItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

}