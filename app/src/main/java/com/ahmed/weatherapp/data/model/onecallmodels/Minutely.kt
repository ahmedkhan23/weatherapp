package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import com.ahmed.weatherapp.data.model.DUMMY_INT
import kotlinx.serialization.Serializable

@Serializable
data class Minutely(
    val dt: Int = DUMMY_INT,
    val precipitation: Double = DUMMY_DOUBLE
)