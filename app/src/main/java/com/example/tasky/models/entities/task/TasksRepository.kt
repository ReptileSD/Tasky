package com.example.tasky.models.entities.task

import com.example.tasky.models.TasksDatabase
import com.example.tasky.models.entities.subtask.Subtask
class TasksRepository(private val db: TasksDatabase) {
    suspend fun insert(task: Task) = db.getTasksDao().insert(task)

    suspend fun delete(task: Task) = db.getTasksDao().delete(task)

    suspend fun update(task: Task) = db.getTasksDao().update(task)

    fun getAllTasks() = db.getTasksDao().getAllTasks()

    fun getImportantTasks() = db.getTasksDao().getImportantTasks()

    fun getCompletedTasks() = db.getTasksDao().getCompletedTasks()

    suspend fun insertSubtask(subtask: Subtask) = db.getSubtasksDao().insert(subtask)

    suspend fun deleteSubtask(subtask: Subtask) = db.getSubtasksDao().delete(subtask)

    suspend fun updateSubtask(subtask: Subtask) = db.getSubtasksDao().update(subtask)

    fun getAllSubtasks(taskID: Int) = db.getSubtasksDao().getAllSubtasks(taskID)
}