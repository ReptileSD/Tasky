package com.example.tasky.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasky.models.entities.task.TasksRepository

class SubtasksViewModelFactory (
    private val application: Application,
    private val repository: TasksRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubtasksViewModel(application, repository) as T
    }
}