package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double = -1000.0,
    val lon: Double = -1000.0
)