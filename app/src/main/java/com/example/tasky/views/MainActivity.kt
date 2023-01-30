package com.example.tasky.views

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.other.CreateTaskDialog
import com.example.tasky.other.TasksItemTouchHelper
import com.example.tasky.other.TasksListAdapter
import com.example.tasky.databinding.ActivityMainBinding
import com.example.tasky.models.Task
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.models.TasksDatabase
import com.example.tasky.models.TasksRepository
import com.example.tasky.viewModels.TasksViewModelFactory
import android.content.Intent
import android.widget.Toast
class MainActivity : AppCompatActivity(), CreateTaskDialog.CreateTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksList: MutableList<Task>
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val database = TasksDatabase(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter = TasksListAdapter(viewModel.getAllTasks().value?.reversed() ?: listOf()) {
            Intent(this, EditTaskActivity::class.java).also {
                startActivity(it)
            }
        }
        layoutManager = LinearLayoutManager(this)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getAllTasks().observe(this, tasksObserver)
        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)

        setContentView(binding.root)

        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager
            rvTasks.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )

            fbtnAdd.setOnClickListener {
                showCreateTaskDialog()
            }
        }
    }

    private fun showCreateTaskDialog() {
        CreateTaskDialog().show(supportFragmentManager, "Add task")
    }

    override fun updateTasks(title: String, task: String) {
        val newTask = Task(title, task, false)
        viewModel.addTask(newTask)
    }
}