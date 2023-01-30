package com.example.tasky.views

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
import com.example.tasky.models.TaskSerializer
class MainActivity : AppCompatActivity(), CreateTaskDialog.CreateTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksList: MutableList<Task>
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        adapter =
            TasksListAdapter(viewModel.getAllTasks().value?.reversed() ?: listOf(), viewModel) {
                Intent(this, EditTaskActivity::class.java).also { intent ->
                    intent.putExtra("Task", TaskSerializer.fromTask(it))
                    startActivity(intent)
                }
            }
        layoutManager = LinearLayoutManager(this)
        val tasksObserver = Observer<List<Task>> {
            adapter.tasks = it.reversed()
            adapter.notifyDataSetChanged()
        }
        viewModel.getAllTasks().observe(this, tasksObserver)
        val itemTouchHelper = TasksItemTouchHelper(viewModel, adapter, binding.root)
        itemTouchHelper.attachToRecyclerView(binding.rvTasks)

        setContentView(binding.root)

        binding.apply {
            rvTasks.adapter = adapter
            rvTasks.layoutManager = layoutManager


            fbtnAdd.setOnClickListener {
                CreateTaskDialog().show(supportFragmentManager, "Add task")            }
        }
    }



    override fun addTask(title: String, task: String, date: String) {
        val newTask = Task(title, task, false, date)
        viewModel.add(newTask)
    }
}