package com.example.yatodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)

        val closeButton = findViewById<FloatingActionButton>(R.id.close_button)
        closeButton.setOnClickListener {
            finish()
        }

        // Popup menu handling
        val popupTextView = findViewById<TextView>(R.id.popup_button)
        popupTextView.setOnClickListener {
            val popupMenu = PopupMenu(this, popupTextView)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setForceShowIcon(true)
            val importanceHighItem = popupMenu.menu.getItem(2)
            importanceHighItem.icon = AppCompatResources.getDrawable(
                this,
                R.drawable.double_exclamation_
            )
            val s = SpannableString(importanceHighItem.title)
            s.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),
                0, s.length, 0
            )
            importanceHighItem.title = s
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.importance_none ->
                        popupTextView.text = item.title

                    R.id.importance_low ->
                        popupTextView.text = item.title

                    R.id.importance_high ->
                        popupTextView.text = item.title
                }
                true
            }
            popupMenu.show()
        }
    }
}