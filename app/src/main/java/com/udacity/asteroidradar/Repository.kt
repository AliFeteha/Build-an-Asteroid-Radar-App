package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.api.Network.service
import com.udacity.asteroidradar.database.AsteroidsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AsteroidRepository(private val database: AsteroidsDatabase) {
    companion object {
        const val ASTEROIDS_DATE_FORMAT = "yyyy-MM-dd"
    }
    suspend fun refreshAsteroid(){
        val date = SimpleDateFormat(ASTEROIDS_DATE_FORMAT,Locale.getDefault())
        val current = date.format( getDate())
        val endDate = date.format( getEndDate())
        withContext(Dispatchers.IO){
            try{
                database.asteroidDao.deleteAllBefore(current)
                val astroids : List<Asteroid> = parseAsteroidsJsonResult(JSONObject(service.getAsteroids(current,endDate)))
                Log.i("lol","${astroids.size}")
                database.asteroidDao.insertAll(*astroids.asDatabase().toTypedArray())
                Log.i("lol","Astroids in data base")
            }catch (e:Exception){
                Log.i("lol",e.toString())
            }

        }
    }
    suspend fun refreshPictureOfDay(){
        val date = SimpleDateFormat(ASTEROIDS_DATE_FORMAT,Locale.getDefault())
        var current = date.format( getDate())
        withContext(Dispatchers.IO){
            try{
                val pictureOfDay : PictureOfDay = parsePictureJsonResult(JSONObject(service.getPicture()))
                Log.i("lol","${pictureOfDay.url}")
                database.pictureOfDay.insert(pictureOfDay.asDatabase())
                Log.i("lol","picture in data base")
            }catch (e:Exception){
                Log.i("lol",e.toString())
            }
        }
}}