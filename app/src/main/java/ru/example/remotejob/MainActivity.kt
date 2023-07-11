package ru.example.remotejob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.example.remotejob.databinding.ActivityMainBinding
import ru.example.remotejob.db.FavouriteJobDatabase
import ru.example.remotejob.repository.RemoteJobRepository
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModel
import ru.example.remotejob.ui.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel:RemoteJobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setupViewModel()
    }

    private fun setupViewModel(){
        val remoteJobRepository = RemoteJobRepository(
            FavouriteJobDatabase(this)
        )
        val viewModelProviderFactory = RemoteJobViewModelFactory(
            application,
            remoteJobRepository
        )
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(RemoteJobViewModel::class.java)
    }
}