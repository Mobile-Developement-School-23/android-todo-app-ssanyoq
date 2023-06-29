package com.example.yatodo.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException
import java.util.Calendar
import java.util.Date

enum class Importance {
    LOW, COMMON, HIGH
}
@Parcelize
data class TodoItem(
    var taskId: String,
    var text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    var createdAt: Date,
    var modifiedAt: Date?,
): Parcelable