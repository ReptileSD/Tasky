package com.example.tasky.models.entities.task

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasky.models.entities.task.Task

@Dao
abstract class TasksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(task: Task)

    @Delete
    abstract suspend fun deleteTask(task: Task)

    @Transaction
    open suspend fun delete(task: Task) {
        deleteAllSubtasks(task.ID as Int)
        deleteTask(task)
    }
    @Update(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun update(task: Task)

    @Query("SELECT * FROM Tasks")
    abstract fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks WHERE isImportant = 1")
    abstract fun getImportantTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks WHERE isCompleted = 1")
    abstract fun getCompletedTasks(): LiveData<List<Task>>

    @Query("DELETE FROM Subtasks WHERE taskID = :taskID")
    abstract suspend fun deleteAllSubtasks(taskID: Int)
}