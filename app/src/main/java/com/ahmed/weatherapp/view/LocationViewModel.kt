package com.ahmed.weatherapp.view

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.LocationWeatherRepository
import com.ahmed.weatherapp.data.NetworkResult
import com.ahmed.weatherapp.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationViewModel(private val locationRepository: LocationWeatherRepository) : ViewModel(), DefaultLifecycleObserver {

    init {
        getCurrentWeather()
    }

    val locationDataState = MutableStateFlow(LocationWeatherData())

    private fun getCurrentWeather() {
        viewModelScope.launch(context = Dispatchers.IO) {
            when (val weatherResponse = locationRepository.getCurrentLocationWeather()) {
                is NetworkResult.Success -> {

                    locationDataState.update {
                        it.copy(weather = weatherResponse.data, updated = true, currentDateTime = getCurrentDateAndTime())
                    }
                }

                is NetworkResult.Error -> {
                    locationDataState.update {
                        it.copy(weather = WeatherResponse.error().copy(errorMsg = weatherResponse.error, error = true),
                            updated = true,
                            currentDateTime = getCurrentDateAndTime())
                    }
                }
            }
        }

    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "${this@LocationViewModel::class.java.name} onPause")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "${this@LocationViewModel::class.java.name} onResume")

        locationDataState.update {
            it.copy(weather = WeatherResponse.dummy(), updated = false, currentDateTime = "")
        }
        getCurrentWeather()
    }
}


data class LocationWeatherData(
    val weather: WeatherResponse = WeatherResponse.dummy(),
    val updated: Boolean = false,
    var currentDateTime: String = getCurrentDateAndTime()
)

fun getCurrentDateAndTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
        Date())

}

