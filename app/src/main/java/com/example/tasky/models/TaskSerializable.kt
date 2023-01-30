package com.example.tasky.models
import java.io.Serializable

data class TaskSerializable(
    val title: String,
    val task: String,
    val isCompleted: Boolean,
    val ID: Int? = null
) : Serializable

{

    companion object {
        fun toTask(src: TaskSerializable): Task {
            return Task(
                src.title,
                src.task,
                src.isCompleted,
                src.ID
            )
        }

        fun fromTask(src: Task): TaskSerializable {
            return TaskSerializable(
                src.title,
                src.task,
                src.isCompleted,
                src.ID
            )
        }
    }
}