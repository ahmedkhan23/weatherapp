package com.ahmed.weatherapp.data.model.onecallmodels

import com.ahmed.weatherapp.data.model.DUMMY_DOUBLE
import kotlinx.serialization.Serializable

@Serializable
data class Temp(
    val day: Double = DUMMY_DOUBLE,
    val eve: Double = DUMMY_DOUBLE,
    val max: Double = DUMMY_DOUBLE,
    val min: Double = DUMMY_DOUBLE,
    val morn: Double = DUMMY_DOUBLE,
    val night: Double = DUMMY_DOUBLE
)