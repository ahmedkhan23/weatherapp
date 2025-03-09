package com.ahmed.weatherapp.data

import android.location.Location
import androidx.lifecycle.ViewModel

class LocationViewModel(private val locationRepository: LocationRepositoryImpl) : ViewModel() {

    fun getCurrentLocation(callback: (Location) -> Unit) {
        locationRepository.getCurrentLocation(callback)
    }


}