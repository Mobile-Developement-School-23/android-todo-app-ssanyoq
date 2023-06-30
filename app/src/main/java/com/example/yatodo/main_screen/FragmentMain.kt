package com.example.yatodo.main_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yatodo.R
import com.example.yatodo.App
import com.example.yatodo.components.FragmentMainComponent
import com.example.yatodo.components.FragmentMainViewComponent
import com.example.yatodo.data.TodoItemsRepository
import com.example.yatodo.recycler.TodoItemAdapter
import com.example.yatodo.viewmodel.TodoItemsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView

class FragmentMain : Fragment(R.layout.fragment_main) {
    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var fragmentComponent: FragmentMainComponent
    private var fragmentViewComponent: FragmentMainViewComponent? = null

    private val viewModel: TodoItemsViewModel by viewModels { applicationComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent =
            FragmentMainComponent( // крашится тут, очень очень долго пытался починить - не чинится
                applicationComponent,
                viewModel = viewModel,
                fragment = this
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
            todoItemsController.setUpViews()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val todoItemsRepository = TodoItemsRepository()
//        todoItemsRepository.generate()
//
//        val todoItemsRecyclerView = view.findViewById<RecyclerView>(R.id.todo_items)
//        val todoItemAdapter = TodoItemAdapter { id -> onItemClicked(id, todoItemsRepository) }
//        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//        todoItemsRecyclerView.adapter = todoItemAdapter
//        todoItemsRecyclerView.layoutManager = layoutManager
//        todoItemAdapter.todoItems = todoItemsRepository.getTodoItems()
//
//        val statusLabel = view.findViewById<TextView>(R.id.helper_text)
//        statusLabel.text = getString(R.string.tasks_done, todoItemsRepository.countDone())

        val appBarLayout = view.findViewById<AppBarLayout>(R.id.app_bar_layout)
        val motionLayout = view.findViewById<MotionLayout>(R.id.toolbar)
        appBarLayout?.addOnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            if (motionLayout != null) {
                motionLayout.progress = seekPosition
            }
        }


        // New item button handling
        val newItemButton = view.findViewById<FloatingActionButton>(R.id.new_item_button)
        newItemButton.setOnClickListener {
            this.findNavController()
                .navigate(R.id.action_main_to_task_fragment, bundleOf("todo_item" to null))
        }

        // Visibility button handling
        var isVisibilityTurnedOn = true
        val visibilityButton = view.findViewById<ShapeableImageView>(R.id.visibilityIcon)
        visibilityButton.setOnClickListener {
            if (isVisibilityTurnedOn) {
                visibilityButton.setImageDrawable(
                    this.context?.let { it1 ->
                        AppCompatResources.getDrawable(
                            it1,
                            R.drawable.visibility_off
                        )
                    }
                )
            } else {
                visibilityButton.setImageDrawable(
                    this.context?.let { it1 ->
                        AppCompatResources.getDrawable(
                            it1,
                            R.drawable.visibility
                        )
                    }
                )
            }
            isVisibilityTurnedOn = !isVisibilityTurnedOn

        }
    }

    private fun onItemClicked(id: String?, todoItemsRepository: TodoItemsRepository) {
        val newItemButton = view?.findViewById<FloatingActionButton>(R.id.new_item_button)
        val bundle = Bundle()
        bundle.putParcelable("todo_item", todoItemsRepository.getItemById(id))
        newItemButton?.setOnClickListener {
            this.findNavController().navigate(R.id.action_main_to_task_fragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentViewComponent = null
    }
}