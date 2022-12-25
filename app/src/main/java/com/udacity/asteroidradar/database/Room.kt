package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import java.util.*

@Dao
interface AsteroidDao{
    @Query("select * from DatabaseAsteroid" )
    fun getAsteroid():LiveData<List<DatabaseAsteroid>>
    @Query("select * from DatabaseAsteroid WHERE closeApproachDate >= :startDate AND closeApproachDate<= :endDate " )
    fun getWeaklyAsteroid(startDate: String,endDate:String):LiveData<List<DatabaseAsteroid>>
    @Query("SELECT * FROM DatabaseAsteroid WHERE closeApproachDate == :day ORDER by closeApproachDate ASC")
    fun getTodayAsteroids(day: String) : LiveData<List<DatabaseAsteroid>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)
    @Query("DELETE FROM DatabaseAsteroid WHERE closeApproachDate < :lastDate")
    fun deleteAllBefore(lastDate: String): Int
}
@Dao
interface PictureOfDayDao{
    @Query("select * from DatabasePicture")
    fun getPhotoOfDay():LiveData<DatabasePicture>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg PhotoOfDay:DatabasePicture)
}
@Database(entities = [DatabaseAsteroid::class,DatabasePicture::class], version = 2)
abstract class AsteroidsDatabase :RoomDatabase(){
    abstract val asteroidDao : AsteroidDao
    abstract val pictureOfDay : PictureOfDayDao
}
private lateinit var INSTANCE :  AsteroidsDatabase
fun getDataBase(context: Context):AsteroidsDatabase{
    synchronized(AsteroidsDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,AsteroidsDatabase::class.java,"Astroids"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}
