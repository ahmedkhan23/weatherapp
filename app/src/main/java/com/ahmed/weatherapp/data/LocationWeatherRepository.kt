package com.ahmed.weatherapp.data

import com.ahmed.weatherapp.data.model.WeatherResponse
import com.ahmed.weatherapp.domain.CompactWeatherData
import com.ahmed.weatherapp.domain.DayWeatherItemData

interface LocationWeatherRepository {

    suspend fun getCurrentLocation()
    suspend fun getCurrentLocationWeather(): NetworkResult<WeatherResponse>
    suspend fun getLocationWeatherAndForecast(): NetworkResult<List<CompactWeatherData>>
    fun getLocationUpdates()
}