package com.example.tasksapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasksapp.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {
    private lateinit var viewModel: EditTaskViewModel
    private var _binding: FragmentEditTaskBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentEditTaskBinding.inflate(inflater,container,false)
        val view=binding.root

        val taskId=EditTaskFragmentArgs.fromBundle(requireArguments()).taskId

        val application= requireNotNull(this.activity).application
        val dao=TaskDatabase.getInstance(application).taskDao

        val viewModelFactory=EditTaskViewModelFactory(taskId,dao)
        val viewModel=ViewModelProvider(this,viewModelFactory)[EditTaskViewModel::class.java]

        binding.viewModel=viewModel
        //let layout use LiveData
        binding.lifecycleOwner=viewLifecycleOwner

        //observe navigateToTask
        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer{navigate->
            if(navigate){
                view.findNavController().navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }

        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}