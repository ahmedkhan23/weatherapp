package com.ahmed.weatherapp.data.model.onecallmodels

import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)