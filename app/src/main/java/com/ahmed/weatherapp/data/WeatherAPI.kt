package com.ahmed.weatherapp.data

import com.ahmed.weatherapp.data.model.WeatherResponse
import com.ahmed.weatherapp.data.model.onecallmodels.OneCallResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("/data/3.0/onecall")
    suspend fun getAllWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Response<OneCallResponse>
}