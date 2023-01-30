package com.example.tasky.views

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.R
import com.example.tasky.databinding.FragmentImportantTasksBinding
import com.example.tasky.databinding.FragmentTasksBinding
import com.example.tasky.models.Task
import com.example.tasky.models.TaskSerializer
import com.example.tasky.models.TasksDatabase
import com.example.tasky.models.TasksRepository
import com.example.tasky.other.CreateTaskDialog
import com.example.tasky.other.TasksItemTouchHelper
import com.example.tasky.other.TasksListAdapter
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.viewModels.TasksViewModelFactory
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.activity.result.ActivityResultLauncher


class ImportantTasksFragment : Fragment() {
    private lateinit var binding: FragmentImportantTasksBinding
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel
    private lateinit var activityContext: Context
    private lateinit var application: Application
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImportantTasksBinding.inflate(inflater, container, false)
        val database = TasksDatabase.getInstance(activityContext)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter =
            TasksListAdapter(viewModel.getImportantTasks().value?.reversed() ?: listOf(), viewModel) {
                openEditTaskActivity(it)
            }
        layoutManager = LinearLayoutManager(activityContext)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getImportantTasks().observe(viewLifecycleOwner, tasksObserver)

        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter, binding.root)
        itemTouchHelper.attachToRecyclerView(binding.rvTasksImportant)
        binding.apply {
            rvTasksImportant.adapter = adapter
            rvTasksImportant.layoutManager = layoutManager
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        application = (context as Activity).application
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 123) {
                val data: Intent = result.data ?: return@registerForActivityResult
                val task = TaskSerializer.toTask(data.getSerializableExtra("Task") as TaskSerializer)
                viewModel.delete(task)
                Snackbar.make(binding.root, R.string.task_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        viewModel.add(task)
                    }
                    .show()
            }
        }
    }
    private fun openEditTaskActivity(task: Task) {
        Intent(requireActivity(), EditTaskActivity::class.java).also { intent ->
            intent.putExtra("Task", TaskSerializer.fromTask(task))
            resultLauncher.launch(intent)
        }
    }
}