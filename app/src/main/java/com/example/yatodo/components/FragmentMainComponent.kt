package com.example.yatodo.components

import com.example.yatodo.domain.TodoItemDiffCalculator
import com.example.yatodo.mainscreen.FragmentMain
import com.example.yatodo.recycler.TodoItemAdapter
import com.example.yatodo.viewmodel.TodoItemsViewModel

/**
 * Stores data necessary for `FragmentMain`
 */
class FragmentMainComponent(
    val viewModel: TodoItemsViewModel,
    val fragment: FragmentMain
) {
    val adapter = TodoItemAdapter(viewModel, TodoItemDiffCalculator())
}
