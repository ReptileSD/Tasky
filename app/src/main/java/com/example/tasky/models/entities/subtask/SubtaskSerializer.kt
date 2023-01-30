package com.example.tasky.models.entities.subtask

import java.io.Serializable

data class SubtaskSerializer(
    var title: String,
    var taskID: Int,
    var ID: Int
) : Serializable
{
    fun toSubtask(src: SubtaskSerializer): Subtask {
        return Subtask(src.title, src.taskID, src.ID)
    }

    fun fromSubtask(src: Subtask): SubtaskSerializer {
        return SubtaskSerializer(src.title, src.taskID, src.ID as Int)
    }
}