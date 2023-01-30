package com.example.tasky.other

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.other.TasksListAdapter
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.models.Task

class TasksItemTouchHelperCallback(
    private val viewModel: TasksViewModel,
    private val adapter: TasksListAdapter
)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
{
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val task = adapter.tasks[viewHolder.adapterPosition]
        viewModel.deleteTask(task)
    }
}