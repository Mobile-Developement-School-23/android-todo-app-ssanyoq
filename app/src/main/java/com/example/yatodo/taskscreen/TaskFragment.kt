package com.example.yatodo.taskscreen

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.example.yatodo.R
import com.example.yatodo.components.TaskFragmentComponent
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

class TaskFragment : Fragment(R.layout.task_fragment) {

    private val taskFragmentComponent = TaskFragmentComponent(arguments)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val closeButton = view.findViewById<FloatingActionButton>(R.id.close_button)
        val importancePopup = view.findViewById<TextView>(R.id.popup_button)
        val datePickerIndicator = view.findViewById<TextView>(R.id.datepicker_indicator)
        val datePickerButton = view.findViewById<SwitchCompat>(R.id.toggle_date_button)
        val saveButton = view.findViewById<MaterialButton>(R.id.save_button)
        val deleteButton = view.findViewById<ConstraintLayout>(R.id.delete_layout)


        closeButton.setOnClickListener {
            setResultAndPop(null, InteractionType.Nothing)
        }
        deleteButton.setOnClickListener {
            setResultAndPop(taskFragmentComponent.todoItem, InteractionType.DeleteItem)
        }
        saveButton.setOnClickListener {
            onSaveButtonPress()
        }
        // Popup menu handling
        importancePopup.setOnClickListener {
            onImportancePopupPress()
        }

        datePickerButton.setOnClickListener { button ->
            onDatePickerButtonPress(button)
        }

        // setting passed todoItem everywhere
        val taskDescription = view.findViewById<EditText>(R.id.task_description)
        if (taskFragmentComponent.todoItem != null) {
            taskDescription.setText(
                taskFragmentComponent.todoItem.text,
                TextView.BufferType.EDITABLE
            )
            importancePopup.text = getString(taskFragmentComponent.todoItem.importance.stringId())
            if (taskFragmentComponent.todoItem.deadline != null) {
                datePickerButton.isChecked = true
                datePickerIndicator.text = setDate(taskFragmentComponent.todoItem.deadline!!)
            }

            taskFragmentComponent.importance = taskFragmentComponent.todoItem.importance
            taskFragmentComponent.pickedDate = taskFragmentComponent.todoItem.deadline

        }
    }

    private fun setDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return sdf.format(date)
    }

    /**
     * Handles save button presses
     */
    private fun onSaveButtonPress() {
        val taskDescription = view?.findViewById<EditText>(R.id.task_description)
        taskFragmentComponent.taskText = taskDescription?.text.toString()
        val newItem = taskFragmentComponent.composeTodoItem()
        var interaction = InteractionType.AddItem
        if (taskFragmentComponent.todoItem != null) {
            newItem?.taskId = taskFragmentComponent.todoItem.taskId
            interaction = InteractionType.ChangeItem
        }
        setResultAndPop(newItem, interaction)
    }

    /**
     * Opens `MaterialDatePicker` on [button] press. Unpresses [button] if calendar was dismissed,
     * sets value in `taskFragmentComponent` and in TextView
     */
    private fun onDatePickerButtonPress(button: View?) {
        button as SwitchCompat
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
        val datePickerIndicator = view?.findViewById<TextView>(R.id.datepicker_indicator)
        val datePickerButton = view?.findViewById<SwitchCompat>(R.id.toggle_date_button)
        if (button.isChecked) {
            datePicker.show(this.parentFragmentManager, "tag")
            datePicker.addOnPositiveButtonClickListener { it2 ->
                taskFragmentComponent.pickedDate = Date(it2)
                datePickerIndicator?.text = setDate(taskFragmentComponent.pickedDate!!)
            }
            datePicker.addOnDismissListener {
                if (taskFragmentComponent.pickedDate == null) {
                    datePickerButton?.isChecked = false
                }
            }
        } else {
            taskFragmentComponent.pickedDate = null
            datePickerIndicator?.text = ""
        }
    }

    /**
     * Draws popup menu and handles presses by changing text
     * of closed popup and by changing field in `taskFragmentComponent`
     */
    private fun onImportancePopupPress() {
        val importancePopup = view?.findViewById<TextView>(R.id.popup_button)
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
            importancePopup?.text = item.title
            when (item.itemId) {
                R.id.importance_none ->
                    taskFragmentComponent.importance = Importance.COMMON

                R.id.importance_low ->
                    taskFragmentComponent.importance = Importance.LOW

                R.id.importance_high ->
                    taskFragmentComponent.importance = Importance.HIGH
            }
            true
        }
        popupMenu.show()
    }

    /**
     * Shortcut for quitting Fragment. Sets fragment result with following and pops back stack:
     *
     * `requestKey = "task_fragmnent"`,
     * `result` = bundleOf("todo_item" to [todoItem], "interaction_type" to [interactionType]
     *
     */
    private fun setResultAndPop(todoItem: TodoItem?, interactionType: InteractionType) {
        setFragmentResult(
            "task_fragment", bundleOf(
                "todo_item" to todoItem,
                "interaction_type" to interactionType
            )
        )
        parentFragmentManager.popBackStack()
    }
}
