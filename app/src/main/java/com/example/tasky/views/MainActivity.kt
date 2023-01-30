package com.example.tasky.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.tasky.other.CreateTaskDialog
import com.example.tasky.databinding.ActivityMainBinding
import com.example.tasky.models.entities.task.Task
import com.example.tasky.viewModels.TasksViewModel
import com.example.tasky.models.TasksDatabase
import com.example.tasky.models.entities.task.TasksRepository
import com.example.tasky.viewModels.TasksViewModelFactory
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.tasky.R

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
        val isImportant
                = supportFragmentManager.findFragmentByTag("importantTasksFragment") != null
        val isCompleted
                = supportFragmentManager.findFragmentByTag("completedTasksFragment") != null
        val newTask = Task(title, task, isCompleted, isImportant, date)
        viewModel.add(newTask)
    }
    private fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        popup.inflate(R.menu.burger_menu)
        popup.setOnMenuItemClickListener { menuItemClickListener(it) }
        popup.show()
    }

private fun menuItemClickListener(it: MenuItem): Boolean {
    when (it.itemId) {
        R.id.miAllTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, allTasksFragment, "allTasksFragment")
                commit()
            }
            return true
        }
        R.id.miImportantTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, importantTasksFragment, "importantTasksFragment")
                commit()
            }
            return true
        }
        R.id.miCompletedTasks -> {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frgTasks, completedTasksFragment, "completedTasksFragment")
                commit()
            }
            return true
        }
        else -> return false
    }
}
}