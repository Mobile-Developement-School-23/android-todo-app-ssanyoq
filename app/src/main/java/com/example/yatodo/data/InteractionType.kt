package com.example.yatodo.data

import kotlinx.parcelize.Parcelize

enum class InteractionType {
    AddItem, // for main -> task only
    ChangeItem, // for both main -> task and task -> main
    DeleteItem, // for task -> main only
    Nothing // for task -> main only
}