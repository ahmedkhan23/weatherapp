package com.ahmed.weatherapp.data

import com.ahmed.weatherapp.data.model.WeatherResponse

interface LocationWeatherRepository {

    suspend fun getCurrentLocation()
    suspend fun getCurrentLocationWeather(): WeatherResponse?
    fun getLocationUpdates()
}