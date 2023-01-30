package com.example.tasky.models.entities.subtask

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubtasksDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subtask: Subtask)

    @Delete
    suspend fun delete(subtask: Subtask)

    @Query("SELECT * FROM Subtasks WHERE 'taskID' = :taskID")
    fun getAllSubtasks(taskID: Int): LiveData<List<Subtask>>
}