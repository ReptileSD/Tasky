package com.example.tasky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity(), CreateTaskDialog.createTaskDialogInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksList: MutableList<Task>
    private lateinit var adapter: TasksListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        setContentView(R.layout.activity_main)
        tasksList = mutableListOf(
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
            Task("Вася епт", "Помой посуду, джва дня уже стоит", false),
        )
        adapter = TasksListAdapter(tasksList)
        layoutManager = LinearLayoutManager(this)
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
                addNewTask()
            }
        }
    }

    private fun addNewTask() {
        val dialog = CreateTaskDialog()
        dialog.show(supportFragmentManager, "Add task")
    }

    override fun sendData(title: String, task: String) {
        val newTask = Task(title, task, false)
        tasksList.add(newTask)
        adapter.notifyItemInserted(tasksList.indices.last)
    }
}