package ru.example.remotejob.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.example.remotejob.MainActivity
import ru.example.remotejob.R
import ru.example.remotejob.databinding.FragmentJobDetailsBinding
import ru.example.remotejob.models.FavouriteJob
import ru.example.remotejob.models.Job
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModel


class JobDetailsFragment : Fragment(R.layout.fragment_job_details) {
    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var currentJob: Job
    private val args: JobDetailsFragmentArgs by navArgs()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    _binding = FragmentJobDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!
        setUpWebView()
        binding.fabAddFavorite.setOnClickListener{
            addFavouriteJob(view)
        }
    }

    private fun addFavouriteJob(view: View){
        val favouriteJob = FavouriteJob(
            0,
             currentJob.candidateRequiredLocation,
             currentJob.category   ,
             currentJob.companyLogoUrl,
             currentJob.companyName,
             currentJob.description,
             currentJob.jobType,
             currentJob.salary,
             currentJob.title,
              currentJob.id,
              currentJob.publicationDate,
               currentJob.url
        )

        viewModel.addFavouriteJob(favouriteJob)
        Snackbar.make(view, "Job saved successfully", Snackbar.LENGTH_LONG).show()
    }

    private fun setUpWebView(){
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let {
                loadUrl(it)
            }
        }
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.textZoom = 100
        settings.blockNetworkImage = false
        settings.loadsImagesAutomatically = true


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}