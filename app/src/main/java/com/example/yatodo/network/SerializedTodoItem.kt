package com.example.yatodo.network

import com.example.yatodo.data.TodoItem
import com.example.yatodo.data.apiString
import com.google.gson.annotations.SerializedName

/**
 * Data class made for easier storing of TodoItems that were recieved
 * or that are about to be uploaded via API
 */
data class SerializedTodoItem constructor(
    @SerializedName("id") var id: String,
    @SerializedName("text") var text: String,
    @SerializedName("importance") var importance: String,
    @SerializedName("deadline") var deadline: Long? = null,
    @SerializedName("done") var done: Boolean,
    @SerializedName("color") var color: String? = null,
    @SerializedName("created_at") var createdAt: Long?,
    @SerializedName("changed_at") var changedAt: Long?,
    @SerializedName("last_updated_by") var lastUpdatedBy: String
) {

    // For easier TodoItem -> SerializedTodoItem conversion
    constructor(todoItem: TodoItem) : this(
        id = todoItem.taskId,
        text = todoItem.text,
        importance = todoItem.importance.apiString(),
        deadline = todoItem.deadline?.time,
        done = todoItem.isCompleted,
        createdAt = todoItem.createdAt.time,
        changedAt = todoItem.modifiedAt?.time,

        color = "#FAFAFA",
        lastUpdatedBy = "username"
    )
    init {
        if (changedAt == null) {
            changedAt = createdAt
        }
    }
}

data class ItemResponse(
    @SerializedName("status") var status: String,
    @SerializedName("revision") var revision: Int,
    @SerializedName("element") val element: SerializedTodoItem
)

data class ListResponse(
    @SerializedName("status") var status: String,
    @SerializedName("revision") var revision: Int,
    @SerializedName("list") var list: List<SerializedTodoItem>
)

data class ListRequest(
    @SerializedName("list") var list: List<SerializedTodoItem>
)

data class ItemRequest(
    @SerializedName("element") var element: SerializedTodoItem
)
