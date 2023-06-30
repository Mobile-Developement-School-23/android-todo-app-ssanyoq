package com.example.yatodo.components

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.example.yatodo.main_screen.TodoItemsController

class FragmentMainViewComponent(
    fragmentComponent: FragmentMainComponent,
    root: View,
    lifecycleOwner: LifecycleOwner
    ){
    val todoItemsController = TodoItemsController(
        fragmentComponent.fragment.requireActivity(),
        root,
        fragmentComponent.adapter,
        lifecycleOwner,
        fragmentComponent.viewModel
    )
}