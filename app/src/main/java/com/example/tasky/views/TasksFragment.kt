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
import com.example.tasky.databinding.FragmentTasksBinding
import com.example.tasky.models.TasksDatabase
import com.example.tasky.other.TasksItemTouchHelper
import com.example.tasky.other.TasksListAdapter
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.viewModels.TasksViewModelFactory
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.activity.result.ActivityResultLauncher
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import android.view.MenuItem
import android.util.Log
import com.example.tasky.databinding.ActivityMainBinding
import com.example.tasky.models.entities.task.TasksRepository
import com.example.tasky.other.Consts
import com.example.tasky.models.entities.task.Task
import com.example.tasky.models.entities.task.TaskSerializer

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
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
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        val database = TasksDatabase.getInstance(activityContext)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter =
            TasksListAdapter(viewModel.getAllTasks().value?.reversed() ?: listOf(), viewModel) {
                openEditTaskActivity(it)
            }
        layoutManager = LinearLayoutManager(activityContext)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getAllTasks().observe(viewLifecycleOwner, tasksObserver)

        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter, binding.root)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)
        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.findViewById<ImageButton>(R.id.moreOptions)?.setOnClickListener {
            showPopup(it)
        }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Consts.EDIT_TASK_RESULT_CODE) {
                val data: Intent = result.data ?: return@registerForActivityResult
                val task = TaskSerializer.toTask(data.getSerializableExtra(Consts.TASK_EXTRA) as TaskSerializer)
                viewModel.delete(task)
                Snackbar.make(binding.root, R.string.task_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        viewModel.add(task)
                    }
                    .show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context
        application = (context as Activity).application
    }
    private fun openEditTaskActivity(task: Task) {
        Intent(requireActivity(), EditTaskActivity::class.java).also { intent ->
            intent.putExtra(Consts.TASK_EXTRA, TaskSerializer.fromTask(task))
            resultLauncher.launch(intent)
        }
    }
    private fun showPopup(v: View) {
        val popup = PopupMenu(activityContext, v)
        popup.inflate(R.menu.options_menu)
        popup.setOnMenuItemClickListener {menuItemClickListener(it)}
        popup.show()
    }

    private fun menuItemClickListener(it: MenuItem): Boolean {
        return when(it.itemId) {
            R.id.miSortByName -> {
                adapter.tasks = adapter.tasks.sortedBy { task -> task.title }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.miSortByDate -> {
                adapter.tasks = adapter.tasks.sortedBy { task -> task.date}
                adapter.notifyDataSetChanged()
                true
            }
            R.id.miSortByImportance -> {
                adapter.tasks = adapter.tasks.sortedBy { task -> !task.isImportant }
                adapter.notifyDataSetChanged()
                true
            }
            else -> false
        }
    }
}