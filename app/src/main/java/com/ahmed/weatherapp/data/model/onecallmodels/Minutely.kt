package com.ahmed.weatherapp.data.model.onecallmodels

import kotlinx.serialization.Serializable

@Serializable
data class Minutely(
    val dt: Int,
    val precipitation: Double
)