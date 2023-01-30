package com.example.tasky.models
import java.io.Serializable

data class TaskSerializer(
    var title: String,
    var task: String,
    var isCompleted: Boolean,
    val ID: Int? = null
) : Serializable

{

    companion object {
        fun toTask(src: TaskSerializer): Task {
            return Task(
                src.title,
                src.task,
                src.isCompleted,
                src.ID
            )
        }

        fun fromTask(src: Task): TaskSerializer {
            return TaskSerializer(
                src.title,
                src.task,
                src.isCompleted,
                src.ID
            )
        }
    }
}