package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, val dao: TaskDao) : ViewModel(){
    val task=dao.get(taskId)

    //if _navigateToTask becomes true-> app will be navigated to TaskFragment
    private var _navigateToTask= MutableLiveData<Boolean>(false)
    val navigateToTask: LiveData<Boolean> get()= _navigateToTask

    //updating the task
    fun updateTask(){
      viewModelScope.launch {
          dao.update(task.value!!)
          _navigateToTask.value=true
      }
    }

    //deleting the value
    fun deleteTask(){
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateToTask.value=true
        }
    }
    //setting task done(checked)
    fun doneTask(){
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateToTask.value=true
        }
    }
    //set navigateToTask false after app goes back to TaskFragment
    fun onNavigatedToList(){
        _navigateToTask.value=false
    }

}