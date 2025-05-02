package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLike(
    val day: Double = DUMMY_DOUBLE,
    val eve: Double = DUMMY_DOUBLE,
    val morn: Double = DUMMY_DOUBLE,
    val night: Double = DUMMY_DOUBLE
)