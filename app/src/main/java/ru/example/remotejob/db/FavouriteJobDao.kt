package ru.example.remotejob.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.example.remotejob.models.FavouriteJob


@Dao
interface FavouriteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(job: FavouriteJob)

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllFavouriteJob(): LiveData<List<FavouriteJob>>

    @Delete
    suspend fun deleteFavouriteJob(job: FavouriteJob)
}