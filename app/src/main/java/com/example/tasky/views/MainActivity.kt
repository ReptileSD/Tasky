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
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.example.tasky.R
import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

class MainActivity : AppCompatActivity(), CreateTaskDialog.CreateTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksList: MutableList<Task>
    private lateinit var viewModel: TasksViewModel
    private lateinit var allTasksFragment: TasksFragment
    private lateinit var importantTasksFragment: ImportantTasksFragment
    private lateinit var completedTasksFragment: CompletedTasksFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allTasksFragment = TasksFragment()
        importantTasksFragment = ImportantTasksFragment()
        completedTasksFragment = CompletedTasksFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frgTasks, allTasksFragment)
            commit()
        }

        val database = TasksDatabase.getInstance(this)
        val repository = TasksRepository(database)
        val viewModelFactory = TasksViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TasksViewModel::class.java]

        binding.apply {


            fbtnAdd.setOnClickListener {
                CreateTaskDialog().show(supportFragmentManager, "Add task")
            }
        burgerMenu.setOnClickListener { showPopup(burgerMenu) }
        }
    }



    override fun addTask(title: String, task: String, date: String) {
        val newTask = Task(title, task, isCompleted = false, isImportant = false, date)
        viewModel.add(newTask)
    }
    private fun showPopup(v: View) {
    val popup = PopupMenu(this, v)
    popup.inflate(R.menu.burger_menu)
    popup.setOnMenuItemClickListener {menuItemClickListener(it)}
    popup.show()
}

private fun menuItemClickListener(it: MenuItem): Boolean {
    when(it.itemId) {
        R.id.miAllTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, allTasksFragment)
                commit()
            }
            return true
        }
        R.id.miImportantTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, importantTasksFragment)
                commit()
            }
            return true
        }
        R.id.miCompletedTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, completedTasksFragment)
                commit()
            }
            return true
        }
        else -> return false
    }
}
}