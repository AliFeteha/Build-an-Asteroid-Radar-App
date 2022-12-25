package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.DatabasePicture
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureOfDay( val title: String,
                         val url: String):Parcelable
fun PictureOfDay.asDatabase(): DatabasePicture{
    return DatabasePicture(
        title= this.title,
        url = this.url
    )
}