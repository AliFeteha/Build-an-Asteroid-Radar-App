package com.udacity.asteroidradar.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)
fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid>{
    return map{
        Asteroid(
            id =it.id,
            codename = it.codename,
            closeApproachDate =it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
@Entity
data class DatabasePicture constructor(
    @PrimaryKey
    val title: String,
    val url: String)
fun DatabasePicture.asDomainModel(): PictureOfDay{
    return PictureOfDay(
        title = this.title,
        url =this.url
    )
}
