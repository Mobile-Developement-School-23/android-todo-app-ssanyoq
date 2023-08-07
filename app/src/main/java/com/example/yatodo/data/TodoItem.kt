package com.example.yatodo.data

import android.os.Parcelable
import com.example.yatodo.R
import com.example.yatodo.network.SerializedTodoItem
import kotlinx.parcelize.Parcelize
import java.lang.IllegalArgumentException
import java.util.Date

enum class Importance {
    LOW, COMMON, HIGH
}

/**
 * Converts [Importance] to a resource that is used in UI
 */
fun Importance.stringId() =
    when (this) {
        Importance.COMMON -> R.string.importance_none
        Importance.HIGH -> R.string.importance_high
        Importance.LOW -> R.string.importance_low
    }

/**
 * Converts [Importance] to a [String] that is recognisable by an API
 */
fun Importance.apiString() =
    when (this) {
        Importance.COMMON -> "basic"
        Importance.LOW -> "low"
        Importance.HIGH -> "important"
    }

/**
 * Converts [String] importance to [Importance]
 *
 * Made to work with strings recieved via API:
 *
 * `"low"` -> [Importance.LOW]
 *
 * `"basic"` -> [Importance.COMMON]
 *
 * `"important"` -> [Importance.HIGH]
 *
 * @exception IllegalArgumentException if string differs from strings above
 */
fun enumFromString(string: String): Importance =
    when (string) {
        "low" -> Importance.LOW
        "basic" -> Importance.COMMON
        "important" -> Importance.HIGH
        else -> throw IllegalArgumentException("Unable to convert $string into Importance value")
    }

@Parcelize
data class TodoItem constructor(
    var taskId: String,
    var text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    var createdAt: Date,
    var modifiedAt: Date?,
) : Parcelable {

    // For better SerializedTodoItem -> TodoItem conversion
    constructor(serializedTodoItem: SerializedTodoItem) : this(
        serializedTodoItem.id,
        serializedTodoItem.text,
        enumFromString(serializedTodoItem.importance),
        serializedTodoItem.deadline?.let { Date(it) },
        serializedTodoItem.done,
        Date(serializedTodoItem.createdAt!!),
        serializedTodoItem.changedAt?.let { Date(it) })
}
