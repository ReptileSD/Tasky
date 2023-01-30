package com.example.tasky.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasky.models.Task
import androidx.lifecycle.viewModelScope
import com.example.tasky.models.TasksRepository
import kotlinx.coroutines.launch

class TasksViewModel(application: Application, private val repository: TasksRepository) :
    AndroidViewModel(application) {

    fun add(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }
    fun getAllTasks() = repository.getAllTasks()

    fun getImportantTasks() = repository.getImportantTasks()

    fun getCompletedTasks() = repository.getCompletedTasks()
}