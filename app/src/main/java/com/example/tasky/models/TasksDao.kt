package com.example.tasky.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): LiveData<List<Task>>
}