package com.example.tasky.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tasky.models.entities.subtask.Subtask
import com.example.tasky.models.entities.task.Task

data class TaskWithSubtasks(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "ID",
        entityColumn = "taskID"
    )
    val subtasks: List<Subtask>
)