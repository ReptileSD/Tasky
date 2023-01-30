package com.example.tasky.models.entities.subtask

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subtasks")
data class Subtask(
    val title: String,

    val taskID: Int,

    @PrimaryKey(autoGenerate = true)
    val ID: Int? = null
)