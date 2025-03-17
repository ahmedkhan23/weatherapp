package com.ahmed.weatherapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class LocationViewModel(private val locationRepository: LocationWeatherRepository) : ViewModel() {

    fun getCurrentWeather() {
        val deferredResult = viewModelScope.async(context = Dispatchers.IO) {
             locationRepository.getCurrentLocationWeather()
        }

    }


}