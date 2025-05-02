package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import com.ahmed.weatherapp.data.model.DUMMY_INT
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    val clouds: Int = DUMMY_INT,
    val dew_point: Double = DUMMY_DOUBLE,
    val dt: Int = DUMMY_INT,
    val feels_like: FeelsLike? = null,
    val humidity: Double = DUMMY_DOUBLE,
    val moon_phase: Double = DUMMY_DOUBLE,
    val moonrise: Int = DUMMY_INT,
    val moonset: Int = DUMMY_INT,
    val pop: Double = DUMMY_DOUBLE,
    val pressure: Double = DUMMY_DOUBLE,
    val summary: String = "",
    val sunrise: Int = DUMMY_INT,
    val sunset: Int = DUMMY_INT,
    val temp: Temp = Temp(),
    val uvi: Double = DUMMY_DOUBLE,
    val weather: List<WeatherX> = emptyList(),
    val wind_deg: Double = DUMMY_DOUBLE,
    val wind_gust: Double = DUMMY_DOUBLE,
    val wind_speed: Double = DUMMY_DOUBLE
)