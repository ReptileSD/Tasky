package com.example.tasky.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @ColumnInfo(name = "Title")
    val title: String,

    @ColumnInfo(name = "Task")
    val task: String,

    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,

    @PrimaryKey(autoGenerate = true)
    val ID: Int? = null,

    )