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
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
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
                        it.copy(weather = weatherResponse.data, updated = true, currentDateTime = getCurrentDateAndTime(),
                            temp = getFormattedTempCelcius(weatherResponse.data.main.temp)
                        )
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
            it.copy(weather = WeatherResponse.dummy(), updated = false, currentDateTime = FormattedDateAndTime())
        }
        getCurrentWeather()
    }
}


data class LocationWeatherData(
    val weather: WeatherResponse = WeatherResponse.dummy(),
    val updated: Boolean = false,
    var currentDateTime: FormattedDateAndTime = getCurrentDateAndTime(),
    var temp: String = getFormattedTempCelcius(weather.main.temp)
)

fun getCurrentDateAndTime(): FormattedDateAndTime {

    val localDateTime = LocalDateTime.now()
    val instant = Instant.now()

    val day = localDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val date = localDateTime.format(DateTimeFormatter.ofPattern("MMMM dd yyyy"))
    val time = localDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))

    return FormattedDateAndTime(day, date, time)

}

data class FormattedDateAndTime(
    val day: String = "", val date: String = "", val time: String = ""
)

fun getFormattedTempCelcius(temp: Double): String {
    return String.format(Locale.getDefault(), "%.1f", temp).plus("\u00B0C")
}

