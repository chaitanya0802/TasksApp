package com.example.tasksapp
//abstract class

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    //specifying Room to use methods of TaskDao interface
    abstract val taskDao: TaskDao

    //get a instance of Database class (TaskDatabase)
    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase?=null

        fun getInstance(context: Context): TaskDatabase{     //method to return object of
                                                                //TaskDatabase class
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}