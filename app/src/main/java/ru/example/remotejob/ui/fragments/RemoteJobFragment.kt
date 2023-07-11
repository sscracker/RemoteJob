package ru.example.remotejob.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.example.remotejob.MainActivity
import ru.example.remotejob.R
import ru.example.remotejob.databinding.FragmentRemoteJobBinding
import ru.example.remotejob.ui.adapter.RemoteJobAdapter
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModel
import ru.example.remotejob.utils.Constants


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipe: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobBinding.inflate(inflater,container,false)

        swipe = binding.swipeContainer
        swipe.setOnRefreshListener(this)
        swipe.setColorSchemeColors(Color.RED,
        Color.GREEN,Color.BLUE,Color.DKGRAY)

        swipe.post{
            swipe.isRefreshing = true
            setUpRecyclerView()
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()

    }
    private fun setUpRecyclerView(){
        remoteJobAdapter = RemoteJobAdapter()
        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object: DividerItemDecoration(activity, LinearLayout.HORIZONTAL){})
            adapter = remoteJobAdapter


        }

        fetchingData()
    }

    private fun fetchingData(){
        if (Constants.checkInternetConnection(requireContext())){
            viewModel.remoteJobResult().observe(viewLifecycleOwner){
                    job -> if (job != null){
                remoteJobAdapter.diff.submitList(job.jobs)
                swipe.isRefreshing = false
            }
            }
        } else{
            Toast.makeText(activity,"No internet connection", Toast.LENGTH_SHORT).show()
            swipe.isRefreshing = false
        }

    }

    override fun onRefresh() {
        setUpRecyclerView()
    }

}