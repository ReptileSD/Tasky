package com.example.tasky.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao

    companion object {
        @Volatile
        private lateinit var instance: TasksDatabase

        fun getInstance(context: Context): TasksDatabase {
            synchronized(this) {
                if (!::instance.isInitialized) {
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