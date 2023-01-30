package com.example.tasky.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.AutoMigration
import com.example.tasky.models.entities.subtask.Subtask
import com.example.tasky.models.entities.subtask.SubtasksDao
import com.example.tasky.models.entities.task.Task
import com.example.tasky.models.entities.task.TasksDao

@Database(
    entities = [Task::class, Subtask::class],
    exportSchema = true,
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
    )
abstract class TasksDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao
    abstract fun getSubtasksDao(): SubtasksDao

    companion object {
        @Volatile
        private lateinit var instance: TasksDatabase

        fun getInstance(context: Context): TasksDatabase {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = createDatabase(context)
                }
                return instance
            }
        }

        private fun createDatabase(context: Context): TasksDatabase {
            return Room.databaseBuilder(
                context,
                TasksDatabase::class.java,
                "TasksDatabase"
            ).build()
        }
    }
}