package ru.example.remotejob.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.example.remotejob.MainActivity
import ru.example.remotejob.R
import ru.example.remotejob.databinding.FragmentSearchJobBinding
import ru.example.remotejob.ui.adapter.RemoteJobAdapter
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.Job
import ru.example.remotejob.utils.Constants

class SearchJobFragment : Fragment(R.layout.fragment_search_job) {
    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var jobAdapter: RemoteJobAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        if (Constants.checkInternetConnection(requireContext())){
            searchJob()
            setupRecyclerView()
        } else{
            Toast.makeText(activity,"No internet connection", Toast.LENGTH_SHORT).show()
        }


    }

    private fun searchJob(){
        var job: Job? = null
        binding.etSearch.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (text.toString().isNotEmpty()){
                        viewModel.searchRemoteJob(text.toString())
                    }
                }
            }
        }

        }

    private fun setupRecyclerView(){
        jobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }

        viewModel.searchResult().observe(viewLifecycleOwner){ remoteJobs ->
            if (remoteJobs != null) {
                jobAdapter.diff.submitList(remoteJobs.jobs)
            }
        }
    }
    }


