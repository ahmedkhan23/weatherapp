package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int = -1
)