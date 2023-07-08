package com.example.yatodo.data


/**
 * Enum class for easier transition between `TaskFragment` and `FragmentMain`
 */
enum class InteractionType {
    AddItem, // if you want to add new item
    ChangeItem, // if you want to change item
    DeleteItem, // if you want to delete existing item
    Nothing // if new item is deleted or task fragment is closed
}
