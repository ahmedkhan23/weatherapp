package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import com.ahmed.weatherapp.data.model.DUMMY_INT
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val clouds: Int = DUMMY_INT,
    val dew_point: Double = DUMMY_DOUBLE,
    val dt: Int = DUMMY_INT,
    val feels_like: Double = DUMMY_DOUBLE,
    val humidity: Double = DUMMY_DOUBLE,
    val pressure: Double = DUMMY_DOUBLE,
    val sunrise: Int = DUMMY_INT,
    val sunset: Int = DUMMY_INT,
    val temp: Double = DUMMY_DOUBLE,
    val uvi: Double = DUMMY_DOUBLE,
    val visibility: Int = DUMMY_INT,
    val weather: List<WeatherX> = emptyList(),
    val wind_deg: Int = DUMMY_INT,
    val wind_gust: Double = DUMMY_DOUBLE,
    val wind_speed: Double = DUMMY_DOUBLE
)