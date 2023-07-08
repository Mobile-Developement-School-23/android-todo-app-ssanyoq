package com.example.yatodo.mainscreen

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.yatodo.App
import com.example.yatodo.R
import com.example.yatodo.components.FragmentMainComponent
import com.example.yatodo.components.FragmentMainViewComponent
import com.example.yatodo.data.InteractionType
import com.example.yatodo.data.TodoItem
import com.example.yatodo.viewmodel.TodoItemsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class FragmentMain : Fragment() {
    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: FragmentMainComponent
    private var fragmentViewComponent: FragmentMainViewComponent? = null

    private val viewModel: TodoItemsViewModel by viewModels { applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent =
            FragmentMainComponent(
                viewModel = viewModel,
                this
            )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        fragmentViewComponent = FragmentMainViewComponent(
            fragmentComponent,
            view,
            viewLifecycleOwner
        ).apply {
            todoItemsController.setUpViews(getString(R.string.tasks_done))
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hiding app bar handling
        handleAppBarLayout()
        // New item button handling
        handleAddButton()

        // Gets result from closed fragments(TaskFragment)
        setFragmentResultListener("task_fragment") { _, bundle ->
            handleTaskFragments(bundle)
        }
    }

    /**
     * Function for proper AppBar work
     */
    private fun handleAppBarLayout() {
        val appBarLayout = view?.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val motionLayout = view?.findViewById<MotionLayout>(R.id.toolbar)
        appBarLayout?.addOnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            if (motionLayout != null) {
                motionLayout.progress = seekPosition
            }
        }
    }

    /**
     * Sets listener for add button and sends to TaskFragment on click
     */
    private fun handleAddButton() {
        val newItemButton = view?.findViewById<FloatingActionButton>(R.id.new_item_button)
        newItemButton?.setOnClickListener {
            this.findNavController()
                .navigate(
                    R.id.action_main_to_task_fragment,
                    bundleOf("todo_item" to null)
                )
        }
    }

    /**
     * Processes [bundle] from freshly closed TaskFragment, removes, adds or changes
     * elements according to it
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun handleTaskFragments(bundle: Bundle) {
        viewModel.viewModelScope.launch {
            val todoItem = bundle.getParcelable("todo_item", TodoItem::class.java)
            val interactionType =
                bundle.getSerializable("interaction_type", InteractionType::class.java)
            if (todoItem != null) {
                when (interactionType) {
                    InteractionType.AddItem -> viewModel.addItem(todoItem)
                    InteractionType.DeleteItem -> viewModel.deleteItem(todoItem)
                    InteractionType.ChangeItem -> viewModel.changeItem(todoItem)
                    InteractionType.Nothing -> {}
                    else -> Log.w("FragmentMain", "Unexpected FragmentResult, be careful")
                    // ^impossible outcome, but just in case
                }
            }
        }
    }

    /**
     * Saves our lives from memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}
