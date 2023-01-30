package com.example.tasky.models.entities.task
import java.io.Serializable

data class TaskSerializer(
    var title: String,
    var task: String,
    var isCompleted: Boolean,
    var isImportant: Boolean,
    var date: String,
    val ID: Int? = null
) : Serializable

{

    companion object {
        fun toTask(src: TaskSerializer): Task {
            return Task(
                src.title,
                src.task,
                src.isCompleted,
                src.isImportant,
                src.date,
                src.ID
            )
        }

        fun fromTask(src: Task): TaskSerializer {
            return TaskSerializer(
                src.title,
                src.task,
                src.isCompleted,
                src.isImportant,
                src.date,
                src.ID
            )
        }
    }
}