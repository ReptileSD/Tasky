package com.example.tasky.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.models.entities.subtask.Subtask
import com.example.tasky.models.entities.subtask.SubtasksRepository
import com.example.tasky.models.entities.task.TasksRepository
import kotlinx.coroutines.launch

class SubtasksViewModel(application: Application, private val repository: TasksRepository) :
    AndroidViewModel(application)
{
    fun insert(subtask: Subtask) {
        viewModelScope.launch {
            repository.insertSubtask(subtask)
        }
    }

    fun delete(subtask: Subtask) {
        viewModelScope.launch {
            repository.deleteSubtask(subtask)
        }
    }
    fun update(subtask: Subtask) {
        viewModelScope.launch {
            repository.updateSubtask(subtask)
        }
    }

    fun getAllSubtasks(taskID: Int) = repository.getAllSubtasks(taskID)

}