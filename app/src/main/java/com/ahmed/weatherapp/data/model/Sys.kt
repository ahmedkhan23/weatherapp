package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String = DUMMY,
    val id: Int = DUMMY_INT,
    val sunrise: Int = DUMMY_INT,
    val sunset: Int = DUMMY_INT,
    val type: Int = DUMMY_INT
)