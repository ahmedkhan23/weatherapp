package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int = DUMMY_INT,
    val gust: Double = DUMMY_DOUBLE,
    val speed: Double = DUMMY_DOUBLE
)