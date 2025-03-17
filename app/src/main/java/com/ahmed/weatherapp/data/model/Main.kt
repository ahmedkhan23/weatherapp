package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val feels_like: Double = DUMMY_DOUBLE,
    val grnd_level: Int = DUMMY_INT,
    val humidity: Int = DUMMY_INT,
    val pressure: Int = DUMMY_INT,
    val sea_level: Int = DUMMY_INT,
    val temp: Double = DUMMY_DOUBLE,
    val temp_max: Double = DUMMY_DOUBLE,
    val temp_min: Double = DUMMY_DOUBLE
)