package com.example.tasky.other

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.tasky.viewModels.TasksViewModel

class TasksItemTouchHelper(
    viewModel: TasksViewModel,
    adapter: TasksListAdapter
) : ItemTouchHelper(TasksItemTouchHelperCallback(viewModel, adapter))