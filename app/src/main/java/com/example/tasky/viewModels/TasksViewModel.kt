package com.example.tasky.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasky.models.MockDatabase
import com.example.tasky.models.Task
import androidx.lifecycle.viewModelScope
import com.example.tasky.models.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(application: Application, private val repository: TasksRepository) :
    AndroidViewModel(application) {

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun getAllTasks() = repository.getAllTasks()
}