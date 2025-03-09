package com.ahmed.weatherapp.data

import android.location.Location

interface LocationRepository {

    fun getCurrentLocation(callback: (Location) -> Unit)
    fun getLocationUpdates()
}