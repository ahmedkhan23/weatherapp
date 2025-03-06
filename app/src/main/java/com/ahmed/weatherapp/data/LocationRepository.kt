package com.ahmed.weatherapp.data

interface LocationRepository {

    fun getCurrentLocation()
    fun getLocationUpdates()
}