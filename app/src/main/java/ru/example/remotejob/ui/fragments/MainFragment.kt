package ru.example.remotejob.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import okhttp3.internal.wait
import ru.example.remotejob.R
import ru.example.remotejob.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRabBar()
    }

    private fun setUpRabBar(){
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Jobs", RemoteJobFragment::class.java)
                .add("Search", SearchJobFragment::class.java)
                .add("SavedJobs", SavedJobFragment::class.java)
                .create()
        )
        binding.viewPager.adapter = adapter
        binding.viewPagerTab.setViewPager(binding.viewPager)
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}