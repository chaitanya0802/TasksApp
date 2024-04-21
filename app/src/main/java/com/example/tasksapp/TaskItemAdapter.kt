package com.example.tasksapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.TaskItemBinding

//passing a lambda
class TaskItemAdapter(val clickListener: (taskId: Long)-> Unit) :
    ListAdapter<Task,TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {

    //onCreateViewHolder function for each time recycler view item is displayed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TaskItemAdapter.TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)

    //when data needs to be displayed in item(populate)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item=getItem(position)
        holder.bind(item,clickListener)     //calling bind()
    }

    //inner class(ViewHolder) of adapter
    class TaskItemViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding=TaskItemBinding.inflate(layoutInflater,parent,false)
                return TaskItemViewHolder(binding)
            }
        }
        //bind function for setting data in ViewItem
        fun bind(item: Task,clickListener: (taskId: Long) -> Unit){
            binding.task=item
            binding.root.setOnClickListener{
                clickListener(item.taskId)
            }
        }
    }
}