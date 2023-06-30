package com.example.yatodo

import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.yatodo.data.TodoItem
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
        val saveButton = view.findViewById<FloatingActionButton>(R.id.save_button)

        closeButton.setOnClickListener {
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
                when (item.itemId) {
                    R.id.importance_none ->
                        importancePopup.text = item.title

                    R.id.importance_low ->
                        importancePopup.text = item.title

                    R.id.importance_high ->
                        importancePopup.text = item.title
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
                datePicker.addOnPositiveButtonClickListener {it2 ->
                    val date = Date(it2)
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    datePickerIndicator.text = sdf.format(date)
                }
            } else {
                datePickerIndicator.text = ""
            }
        }

        // setting passed todoItem everywhere
        val todoItem: TodoItem? = arguments?.getParcelable("todo_item")
        if (todoItem != null) {
            taskDescription.setText(todoItem.text, TextView.BufferType.EDITABLE)

        }
        saveButton.setOnClickListener {  }
    }
}