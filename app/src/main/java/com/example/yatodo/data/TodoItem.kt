package com.example.yatodo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
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


fun enumFromString(string: String): Importance =
    when (string) {
        "low" -> Importance.LOW
        "basic" -> Importance.COMMON
        "important" -> Importance.HIGH
        else -> throw IllegalArgumentException("Unable to convert $string into Importance value")
    }

class ImportanceConverter {
    @TypeConverter
    fun fromImportance(importance: Importance): String {
        return when (importance) {
            Importance.HIGH -> "high"
            Importance.COMMON -> "basic"
            Importance.LOW -> "low"
        }
    }

    @TypeConverter
    fun toImportance(string: String): Importance {
        return when (string) {
            "high" -> Importance.HIGH
            "basic" -> Importance.COMMON
            "low" -> Importance.LOW
            else -> throw IllegalArgumentException("Unable to convert $string into Importance value")
        }
    }
}

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
@Parcelize
@Entity(tableName = "todo_items")
@TypeConverters(ImportanceConverter::class, DateConverter::class)
data class TodoItem constructor(
    @PrimaryKey @ColumnInfo var taskId: String,
    @ColumnInfo var text: String,
    @TypeConverters(ImportanceConverter::class) @ColumnInfo var importance: Importance,
    @TypeConverters(DateConverter::class) @ColumnInfo var deadline: Date?,
    @ColumnInfo var isCompleted: Boolean,
    @TypeConverters(DateConverter::class) @ColumnInfo var createdAt: Date,
    @TypeConverters(DateConverter::class) @ColumnInfo var modifiedAt: Date?,
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
