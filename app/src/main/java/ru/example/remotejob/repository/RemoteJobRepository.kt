package ru.example.remotejob.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.example.remotejob.api.ApiInstance
import ru.example.remotejob.db.FavouriteJobDatabase
import ru.example.remotejob.models.FavouriteJob
import ru.example.remotejob.models.RemoteJob

class RemoteJobRepository(private val db: FavouriteJobDatabase) {

    private val remoteJobService = ApiInstance.apiService
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJob?> = MutableLiveData()
    private val searchJobResponseLiveData: MutableLiveData<RemoteJob?> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }


    private fun getRemoteJobResponse(){
        remoteJobService.getRemoteJob().enqueue(
             object : Callback<RemoteJob>{
                override fun onResponse(
                    call: Call<RemoteJob>,
                    response: Response<RemoteJob>
                ) {
                    if (response.body() != null) {
                        remoteJobResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                   remoteJobResponseLiveData.postValue(null)
                    Log.e("Tag", "onFailure: ${t.message}")
                }

            }
        )

    }

    fun searchJobResponse(query: String?){
        remoteJobService.searchJob(query).enqueue(
            object: Callback<RemoteJob>{
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    searchJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    searchJobResponseLiveData.postValue(null)
                }

            }
        )

    }

    fun searchJobResult() : LiveData<RemoteJob?> {
        return searchJobResponseLiveData
    }


    fun remoteJobResult(): LiveData<RemoteJob?> {
        return remoteJobResponseLiveData
    }


    suspend fun addFavouriteJob(job: FavouriteJob) = db.getFavouriteJobDao().addFavouriteJob(job)
    suspend fun deleteJob(job: FavouriteJob) = db.getFavouriteJobDao().deleteFavouriteJob(job)
    fun getAllFavouriteJobs() = db.getFavouriteJobDao().getAllFavouriteJob()
}


