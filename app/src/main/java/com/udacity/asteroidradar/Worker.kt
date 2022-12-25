package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDataBase
import retrofit2.HttpException

class Worker(appContext: Context , parameters: WorkerParameters):CoroutineWorker(appContext,parameters) {
    companion object{
        const val Work_Name = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDataBase(applicationContext)
        val repository = AsteroidRepository(database)
        return try{
            repository.refreshAsteroid()
            Result.success()
        }catch (e:HttpException){
            Result.retry()
        }
    }
}