package com.example.yatodo.recycler

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.yatodo.R
import com.example.yatodo.TodoItemsRepository
import com.example.yatodo.data.Importance
import com.example.yatodo.data.TodoItem
import com.example.yatodo.domain.CommonCallbackImpl
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.function.ToDoubleBiFunction

class TodoItemAdapter : RecyclerView.Adapter<ViewHolder>() {
    var todoItemsRepository = TodoItemsRepository()
    var todoItems = mutableListOf<TodoItem>()
        set(value) {
            todoItemsRepository.set(value)
            val callback = CommonCallbackImpl(
                oldItems = field,
                newItems = value,
                { oldItem: TodoItem, newItem -> oldItem.taskId == newItem.taskId })
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }
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

            else -> throw java.lang.IllegalArgumentException("Invalid ViewHolder type")
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == todoItems.size) {
            ADD_ITEM_TYPE
        } else {
            TODO_ITEM_TYPE
        }
    }

    companion object {
        private const val TODO_ITEM_TYPE = 0
        private const val ADD_ITEM_TYPE = 1
    }

    inner class TodoItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val taskDescription: TextView = itemView.findViewById(R.id.task_description)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val importanceIndicator: ImageView =
            itemView.findViewById(R.id.importance_indicator)
        val deadLineDate: TextView = itemView.findViewById(R.id.deadline_date)
        val infoButton: ImageButton = itemView.findViewById(R.id.info_button)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder as TodoItemViewHolder
        holder.taskDescription.text = todoItem.text
        holder.checkBox.isChecked = todoItem.isCompleted

        if (holder.checkBox.isChecked) {
            holder.taskDescription.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
        }

        if (todoItem.deadline == null) {
            holder.deadLineDate.visibility = View.GONE
        } else {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
            holder.deadLineDate.text = sdf.format(todoItem.deadline!!)
        }

        when (todoItem.importance) {
            Importance.LOW -> {
                holder.importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.arrow_down
                    )
                )
            }

            Importance.COMMON -> {
                // No drawables, just empty space
            }

            Importance.HIGH -> {
                holder.importanceIndicator.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.double_exclamation
                    )
                )
                holder.checkBox.buttonTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.red
                        )
                    )
            }
        }
        holder.checkBox.setOnClickListener {
            todoItem.isCompleted = !todoItem.isCompleted
            notifyDataSetChanged()
        }
    }
}
