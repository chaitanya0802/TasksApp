package com.example.tasksapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        //check if application context is not null
        val application = requireNotNull(this.activity).application

        //getting object of TaskDao interface
        val dao = TaskDatabase.getInstance(application).taskDao

        //getting object of ViewModel via factory
        val viewModelFactory = TaskViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory)[TaskViewModel::class.java]

        //setting data binding variable
        binding.viewModel = viewModel

        //setting lifecycleowner
        binding.lifecycleOwner = viewLifecycleOwner

        //setting a adapter with lambda fun of calling onTaskClicked
        val adapter=TaskItemAdapter{ taskId->
            viewModel.onTaskClicked(taskId)
        }
        binding.tasksList.adapter=adapter   //setting adapter to task_list (RecyclerView)

        //observe live data for task property and submit it to adapter's backing list
        viewModel.tasks.observe(viewLifecycleOwner,Observer{
            it?.let {
                adapter.submitList(it)        //listOf<Task>
            }
        })

        //observe navigateToTask Property so that if it not null then it navigates to other Fragment
        viewModel.navigateToTask.observe(viewLifecycleOwner,Observer{taskId->
            taskId?.let{
                val action=TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(taskId)
                this.findNavController().navigate(action)
                //make navigateToTask null
                viewModel.onTaskNavigated()
            }
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}