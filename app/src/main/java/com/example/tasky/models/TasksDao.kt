package com.example.tasky.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(task: Task)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks WHERE isImportant = 1")
    fun getImportantTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks WHERE isCompleted = 1")
    fun getCompletedTasks(): LiveData<List<Task>>
}