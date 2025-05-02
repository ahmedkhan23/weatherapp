package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_INT
import kotlinx.serialization.Serializable

@Serializable
data class WeatherX(
    val description: String = "",
    val icon: String = "",
    val id: Int = DUMMY_INT,
    val main: String = "",
)