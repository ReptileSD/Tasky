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
        private var instance: TasksDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDB(context).also { instance = it }
        }

        private fun createDB(context: Context): TasksDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TasksDatabase::class.java,
                "TasksDatabase"
            ).build()
        }
    }
}