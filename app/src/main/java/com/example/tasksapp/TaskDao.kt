package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//Data Access Object interface
@Dao
interface TaskDao {
    //use suspend to run code in background thread not on main thread
    //i.e make method coroutine
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    //do not make fun coroutine as fun returns LiveData
    @Query("Select * from task_table where taskId=:taskId")     //get a record
    fun get(taskId: Long) :LiveData<Task>

    @Query("Select * from task_table order by taskId desc")     //get all records
    fun getAll(): LiveData<List<Task>>
}