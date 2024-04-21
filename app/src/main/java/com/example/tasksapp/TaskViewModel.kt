package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName=""

    //getting all tasks by Dao as LiveData<List<Task>>
    var tasks=dao.getAll()      //LiveData<List<Task>>

    //NavigateToTask holds value of taskId for EditTaskFragment
    private val _navigateToTask=MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?> get()= _navigateToTask

    //adding task to database table
    fun addTask(){
        //launch insert(coroutine) in same scope of viewModel
        viewModelScope.launch {
            val task= Task()    //new Task Object
            task.taskName=newTaskName
            dao.insert(task)
        }
    }

    fun onTaskClicked(taskId: Long){
        _navigateToTask.value=taskId
    }
    fun onTaskNavigated(){
        _navigateToTask.value=null
    }
}