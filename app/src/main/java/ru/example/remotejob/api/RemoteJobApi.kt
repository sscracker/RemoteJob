package ru.example.remotejob.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.example.remotejob.models.RemoteJob

interface RemoteJobApi {

    @GET("remote-jobs?limit=18")
    fun getRemoteJob(): Call<RemoteJob>

    @GET("remote-jobs")
    fun searchJob(@Query("search") query: String?): Call<RemoteJob>

}