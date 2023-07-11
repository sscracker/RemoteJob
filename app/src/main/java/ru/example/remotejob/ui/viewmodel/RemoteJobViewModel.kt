package ru.example.remotejob.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.example.remotejob.models.FavouriteJob
import ru.example.remotejob.repository.RemoteJobRepository

class RemoteJobViewModel(
    app:Application,
    private val remoteJobRepository: RemoteJobRepository
):AndroidViewModel(app) {
    fun remoteJobResult() = remoteJobRepository.remoteJobResult()

    fun addFavouriteJob(job: FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.addFavouriteJob(job)
    }

    fun deleteJob(job: FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllFavouriteJobs() = remoteJobRepository.getAllFavouriteJobs()

    fun searchRemoteJob(query: String?) = remoteJobRepository.searchJobResponse(query)
    fun searchResult() = remoteJobRepository.searchJobResult()
}