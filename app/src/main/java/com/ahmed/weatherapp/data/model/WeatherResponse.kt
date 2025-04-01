package com.ahmed.weatherapp.data.model

import kotlinx.serialization.Serializable

const val DUMMY_DOUBLE = -1000.0
const val DUMMY_INT = -1000
const val DUMMY = "dummy"


@Serializable
data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main = Main(),
    val name: String,
    val rain: Rain = Rain(0.0),
    val sys: Sys = Sys(),
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather> = emptyList(),
    val wind: Wind = Wind(),
    val error: Boolean = false,
    val errorMsg: String = ""
) {
    companion object {
        fun dummy(): WeatherResponse {

            return WeatherResponse(
                base = DUMMY,
                clouds = Clouds(0),
                cod = DUMMY_INT,
                coord = Coord(DUMMY_DOUBLE, DUMMY_DOUBLE),
                dt = DUMMY_INT,
                id = DUMMY_INT,
                name = DUMMY,
                timezone = DUMMY_INT,
                visibility = DUMMY_INT,
                weather = emptyList()
            )
        }

        fun error(): WeatherResponse {
            return dummy().copy(error = true)
        }
    }
}

