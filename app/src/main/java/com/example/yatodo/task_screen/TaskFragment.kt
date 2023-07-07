package com.example.yatodo

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.yatodo.data.Importance
import com.example.yatodo.data.InteractionType
import com.example.yatodo.data.TodoItem
import com.example.yatodo.data.stringId
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskFragment() : Fragment(R.layout.task_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val closeButton = view.findViewById<FloatingActionButton>(R.id.close_button)
        val importancePopup = view.findViewById<TextView>(R.id.popup_button)
        val datePickerIndicator = view.findViewById<TextView>(R.id.datepicker_indicator)
        val datePickerButton = view.findViewById<SwitchCompat>(R.id.toggle_date_button)
        val taskDescription = view.findViewById<EditText>(R.id.task_description)
        val saveButton = view.findViewById<MaterialButton>(R.id.save_button)
        val deleteButton = view.findViewById<ConstraintLayout>(R.id.delete_layout)

        val todoItem: TodoItem? = arguments?.getParcelable("todo_item")

        var pickedDate: Date? = null
        var taskText: String? = null
        var importance: Importance = Importance.COMMON


        closeButton.setOnClickListener {
            setFragmentResult(
                "task_fragment",
                bundleOf("todo_item" to null, "interaction_type" to InteractionType.Nothing)
            )
            parentFragmentManager.popBackStack()
        }
        deleteButton.setOnClickListener {
            setFragmentResult(
                "task_fragment",
                bundleOf("todo_item" to todoItem, "interaction_type" to InteractionType.DeleteItem)
            )
            parentFragmentManager.popBackStack()
        }
        saveButton.setOnClickListener {
            taskText = taskDescription.text.toString()
            val newItem = TodoItem(
                "To be changed", // definitely will be changed
                taskText!!,
                importance,
                pickedDate,
                false,
                Date(System.currentTimeMillis()),
                null
            )
            if (todoItem == null) {
                setFragmentResult(
                    "task_fragment",
                    bundleOf("todo_item" to newItem, "interaction_type" to InteractionType.AddItem)
                )
            } else {
                newItem.taskId = todoItem.taskId
                setFragmentResult(
                    "task_fragment",
                    bundleOf(
                        "todo_item" to newItem,
                        "interaction_type" to InteractionType.ChangeItem
                    )
                )
            }
            parentFragmentManager.popBackStack()
        }
        // Popup menu handling
        importancePopup.setOnClickListener {
            val popupMenu = PopupMenu(this.requireContext(), importancePopup)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setForceShowIcon(true)

            // High importance element drawing
            val importanceHighItem = popupMenu.menu.getItem(2)
            importanceHighItem.icon = AppCompatResources.getDrawable(
                this.requireContext(),
                R.drawable.double_exclamation
            )
            val s = SpannableString(importanceHighItem.title)
            s.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this.requireContext(), R.color.red)),
                0, s.length, 0
            )
            importanceHighItem.title = s

            // Low importance element drawing
            val importanceLowItem = popupMenu.menu.getItem(1)
            importanceLowItem.icon = AppCompatResources.getDrawable(
                this.requireContext(),
                R.drawable.arrow_down
            )
            popupMenu.setOnMenuItemClickListener { item ->
                importancePopup.text = item.title
                when (item.itemId) {
                    R.id.importance_none ->
                        importance = Importance.COMMON

                    R.id.importance_low ->
                        importance = Importance.LOW

                    R.id.importance_high ->
                        importance = Importance.HIGH
                }
                true
            }
            popupMenu.show()
        }

        // Date picker construction
        val constraintsBuilder = CalendarConstraints.Builder()
            .setStart(MaterialDatePicker.todayInUtcMilliseconds())
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pick Deadline date")
            .setSelection(
                MaterialDatePicker.todayInUtcMilliseconds()
            )
            .setCalendarConstraints(
                constraintsBuilder.build()
            )
            .build()

        // Date picker call

        datePickerButton.setOnClickListener { it ->
            it as SwitchCompat
            if (it.isChecked) {
                datePicker.show(this.parentFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener { it2 ->
                    pickedDate = Date(it2)
                    datePickerIndicator.text = setDate(pickedDate!!)
                }
                datePicker.addOnDismissListener {
                    if (pickedDate == null) {
                        datePickerButton.isChecked = false
                    }
                }
            } else {
                pickedDate = null
                datePickerIndicator.text = ""
            }
        }

        // setting passed todoItem everywhere

        if (todoItem != null) {
            taskDescription.setText(todoItem.text, TextView.BufferType.EDITABLE)
            importancePopup.text = getString(todoItem.importance.stringId())
            if (todoItem.deadline != null) {
                datePickerButton.isChecked = true
                datePickerIndicator.text = setDate(todoItem.deadline!!)
            }

            importance = todoItem.importance
            pickedDate = todoItem.deadline

        }
    }

    fun setDate(date: Any): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return when (date) {
            is Long -> sdf.format(date)
            is Date -> sdf.format(date)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}