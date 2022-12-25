package com.udacity.asteroidradar.api

import android.media.Image
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface Services{
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date")
        start_date: String,
        @Query("end_date")
        end_date: String,
        @Query("api_key")
        apiKey: String = API_KEY
    ) : String
    @GET("planetary/apod")
    suspend fun getPicture(
        @Query("api_key")
        apiKey: String = API_KEY
    ):String

}
object Network {
    val service :Services by lazy {
        retrofit.create(Services::class.java)
    }
}
