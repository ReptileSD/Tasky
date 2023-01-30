package com.example.tasky.other

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.other.TasksListAdapter
import com.example.tasky.viewModels.TasksViewModel
import android.view.View
import com.example.tasky.R
import com.google.android.material.snackbar.Snackbar

class TasksItemTouchHelperCallback(
    private val viewModel: TasksViewModel,
    private val adapter: TasksListAdapter,
    private val view: View)
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
        viewModel.delete(task)
        Snackbar.make(view, R.string.task_deleted, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo) {viewModel.add(task)}
            .show()
    }
}