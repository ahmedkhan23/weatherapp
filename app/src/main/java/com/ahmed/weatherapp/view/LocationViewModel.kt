package com.ahmed.weatherapp.view

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.LocationWeatherRepository
import com.ahmed.weatherapp.data.NetworkResult
import com.ahmed.weatherapp.data.model.Weather
import com.ahmed.weatherapp.data.model.WeatherResponse
import com.ahmed.weatherapp.domain.CompactWeatherData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class LocationViewModel(private val locationRepository: LocationWeatherRepository,
                        private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel(), DefaultLifecycleObserver {


    private val _locationDataState = MutableStateFlow(LocationWeatherData())
    val locationDataState = _locationDataState.asStateFlow()


    private val _currentAndForecastState = MutableStateFlow(CurrentWeatherAndForecast())
    val currentAndForecastState = _currentAndForecastState.asStateFlow()

    /**
     * Fetches the current weather data and updates the state with the retrieved information.
     *
     * The function handles the following:
     * - Fetching weather data using the repository.
     * - Updating the state with the retrieved weather data, formatted temperature, and icon URL on success.
     * - Updating the state with an error message and dummy weather data on failure.
     */
    fun getCurrentWeather() {
        viewModelScope.launch() {
            when (val weatherResponse = withContext(dispatcher) {
                locationRepository.getCurrentLocationWeather()
            }) {
                is NetworkResult.Success -> {

                    _locationDataState.update {
                        it.copy(weather = weatherResponse.data, updated = true, currentDateTime = getCurrentDateAndTime(),
                            temp = getFormattedTempCelcius(weatherResponse.data.main.temp),
                            iconUrl = getIconUrl(weatherResponse.data.weather)
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _locationDataState.update {
                        it.copy(weather = WeatherResponse.error().copy(errorMsg = weatherResponse.error, error = true),
                            updated = true,
                            currentDateTime = getCurrentDateAndTime())
                    }
                }
            }
        }

    }

    /**
     * Fetches the current and forecasted weather data and updates the state with the retrieved information.
     *
     * The function handles the following:
     * - Fetching current and forecasted weather data using the repository.
     * - Updating the state with the retrieved data on success.
     * - Updating the state with an empty list on failure.
     */
    private fun getCurrentAndForecastedWeather() {
        viewModelScope.launch {
            val currentAndForecastWeatherResponse = locationRepository.getLocationWeatherAndForecast()

            when (currentAndForecastWeatherResponse) {
                is NetworkResult.Success -> {
                    _currentAndForecastState.update { it.copy(currentWeatherAndForecast = currentAndForecastWeatherResponse.data, updated = true) }
                }

                is NetworkResult.Error -> {
                    _currentAndForecastState.update {
                        it.copy(currentWeatherAndForecast = emptyList(), updated = true)
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

        _locationDataState.update {
            it.copy(weather = WeatherResponse.dummy(), updated = false, currentDateTime = FormattedDateAndTime(), iconUrl = "")
        }
        getCurrentWeather()
        getCurrentAndForecastedWeather()
    }
}


data class LocationWeatherData(
    val weather: WeatherResponse = WeatherResponse.dummy(),
    val updated: Boolean = false,
    var currentDateTime: FormattedDateAndTime = getCurrentDateAndTime(),
    var temp: String = getFormattedTempCelcius(weather.main.temp),
    var iconUrl: String = ""
)

data class CurrentWeatherAndForecast(
    val currentWeatherAndForecast: List<CompactWeatherData> = emptyList(),
    val updated: Boolean = false
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

fun getIconUrl(weatherList: List<Weather>) : String {

    return if (weatherList.isEmpty()) ""
    else
        "https://openweathermap.org/img/wn/${weatherList.first().icon}@2x.png"
}

