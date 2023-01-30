package com.example.tasky.other

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.tasky.viewModels.TasksViewModel
import android.view.View
class TasksItemTouchHelper(
    viewModel: TasksViewModel,
    adapter: TasksListAdapter,
    view: View
) : ItemTouchHelper(TasksItemTouchHelperCallback(viewModel, adapter, view))