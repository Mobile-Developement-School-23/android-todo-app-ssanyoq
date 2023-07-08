package com.example.yatodo.components

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.yatodo.mainscreen.TodoItemsController

/**
 * Stores view-related data for `FragmentMain`.
 * Must be deleted with Fragment for memory leaks safety
 */
class FragmentMainViewComponent(
    fragmentComponent: FragmentMainComponent,
    root: View,
    lifecycleOwner: LifecycleOwner
) {
    val todoItemsController = TodoItemsController(
        fragmentComponent.fragment.requireActivity(),
        root,
        fragmentComponent.adapter,
        lifecycleOwner,
        fragmentComponent.viewModel
    )
}
