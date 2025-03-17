package com.ahmed.weatherapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel(private val locationRepository: LocationWeatherRepository) : ViewModel() {

    init {
        getCurrentWeather()
    }

    val locationDataState = MutableStateFlow(LocationWeatherData())

    private fun getCurrentWeather() {
        viewModelScope.launch(context = Dispatchers.IO) {
            val weatherResponse = locationRepository.getCurrentLocationWeather()

            if (weatherResponse != null) {
                locationDataState.update {
                    it.copy(weather = weatherResponse, updated = true)
                }
            }
        }

    }


}


data class LocationWeatherData(
    val weather: WeatherResponse = WeatherResponse.dummy(),
    val updated: Boolean = false
)
