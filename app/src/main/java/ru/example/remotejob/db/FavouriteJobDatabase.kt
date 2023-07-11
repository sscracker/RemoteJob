package ru.example.remotejob.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.example.remotejob.models.FavouriteJob


@Database(entities = [FavouriteJob::class], version = 1)
abstract class FavouriteJobDatabase: RoomDatabase() {

    abstract fun getFavouriteJobDao(): FavouriteJobDao

    companion object{
        @Volatile
        private var instance: FavouriteJobDatabase?= null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDb(context).also { instance = it }
        }

        private fun createDb(context: Context) = Room.databaseBuilder(context.applicationContext, FavouriteJobDatabase::class.java,"jobs_db" ).build()
    }
}