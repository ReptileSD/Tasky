package com.example.tasky.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasky.models.TasksRepository

class TasksViewModelFactory(
    private val application: Application,
    private val repository: TasksRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(application, repository) as T
    }
}
