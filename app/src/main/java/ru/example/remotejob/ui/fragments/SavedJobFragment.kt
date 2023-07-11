package ru.example.remotejob.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.remotejob.MainActivity
import ru.example.remotejob.R
import ru.example.remotejob.databinding.FragmentSavedJobBinding
import ru.example.remotejob.models.FavouriteJob
import ru.example.remotejob.ui.adapter.FavouriteJobAdapter
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModel

class SavedJobFragment : Fragment(R.layout.fragment_saved_job), FavouriteJobAdapter.OnItemClickListener {
    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var favouriteAdapter: FavouriteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        favouriteAdapter = FavouriteJobAdapter(this)
        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(object : DividerItemDecoration(
                activity, LinearLayout.VERTICAL
            ){})
            adapter = favouriteAdapter
        }

        viewModel.getAllFavouriteJobs().observe(viewLifecycleOwner){
            job ->
            favouriteAdapter.diff.submitList(job)
            updateUi(job)
        }
    }

    private fun updateUi(job:List<FavouriteJob>){
        if (job.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(job: FavouriteJob, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: FavouriteJob){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete job!")
            setMessage("Are you sure you want to delete this job?")
            setPositiveButton("Delete"){_,_ ->
           viewModel.deleteJob(job)
            Toast.makeText(activity,"Job deleted", Toast.LENGTH_SHORT).show()}
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}