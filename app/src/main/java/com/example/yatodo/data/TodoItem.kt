package com.example.yatodo.data

import android.os.Parcelable
import com.example.yatodo.R
import kotlinx.parcelize.Parcelize
import java.util.Date

enum class Importance {
    LOW, COMMON, HIGH
}
fun Importance.stringId() =
    when (this) {
        Importance.COMMON -> R.string.importance_none
        Importance.HIGH -> R.string.importance_high
        Importance.LOW -> R.string.importance_low
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