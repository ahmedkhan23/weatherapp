package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    val `1h`: Double = 0.0
)