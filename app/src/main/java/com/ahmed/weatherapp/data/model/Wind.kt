package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int,
    val gust: Double = 0.0,
    val speed: Double
)