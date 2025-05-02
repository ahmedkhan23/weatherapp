package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import com.ahmed.weatherapp.data.model.DUMMY_INT
import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    val current: Current = Current(),
    val daily: List<Daily> = emptyList(),
    val hourly: List<Hourly> = emptyList(),
    val lat: Double = DUMMY_DOUBLE,
    val lon: Double = DUMMY_DOUBLE,
    val minutely: List<Minutely> = emptyList(),
    val timezone: String = "",
    val timezone_offset: Int = DUMMY_INT
)