package com.udacity.asteroidradar.main
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getDate
import com.udacity.asteroidradar.api.getEndDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDataBase
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.java.Constant

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val data = getDataBase(application)
    val rep = AsteroidRepository(data)
    val Image:LiveData<PictureOfDay?> = Transformations.map(
        data.pictureOfDay.getPhotoOfDay()
    ){
        it?.asDomainModel()
    }
    init {
        refAsteroid()
        refPictureOfDay()
    }
    private val date = SimpleDateFormat(AsteroidRepository.ASTEROIDS_DATE_FORMAT, Locale.getDefault())
    private var current = date.format( getDate())
    private var endDate = date.format( getEndDate())
    var asteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            data.asteroidDao.getAsteroid()
        ){
            it.asDomainModel()
        }
    var weaklyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            data.asteroidDao.getWeaklyAsteroid(current,endDate)
        ){
            it.asDomainModel()
        }
    var dailyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(
            data.asteroidDao.getTodayAsteroids(current)
        ){
            it.asDomainModel()
        }

    private fun refAsteroid(){
        viewModelScope.launch{
            try{
                rep.refreshAsteroid()
                Log.i("lol","viewModel")
            }catch (e:Exception){
                Log.i("lol",e.toString())
            }

        }
    }
    private fun refPictureOfDay(){
        viewModelScope.launch {
            try{
                Log.i("lol","viewModel2")
                rep.refreshPictureOfDay()
            }catch (e:Exception){

                Log.i("lol",e.toString())
            }
        }
    }

}