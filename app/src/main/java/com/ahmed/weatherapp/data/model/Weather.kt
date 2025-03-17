package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String = DUMMY,
    val icon: String = DUMMY,
    val id: Int = DUMMY_INT,
    val main: String = DUMMY
)