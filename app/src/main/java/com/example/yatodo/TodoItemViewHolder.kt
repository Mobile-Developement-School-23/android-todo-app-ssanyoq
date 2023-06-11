package com.example.yatodo

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.time.LocalDate

class TodoItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val taskDescription: TextView = itemView.findViewById(R.id.task_description)
    private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    private val importanceIndicator: ImageView = itemView.findViewById(R.id.importance_indicator)
    private val deadLineDate: TextView = itemView.findViewById(R.id.deadline_date)

    fun onBind(todoItem: TodoItem) {
        taskDescription.text = todoItem.taskText // TODO text cutting
        checkBox.isChecked = todoItem.isCompleted
        deadLineDate.text = todoItem.deadlineDate.toString() // TODO
    }
}