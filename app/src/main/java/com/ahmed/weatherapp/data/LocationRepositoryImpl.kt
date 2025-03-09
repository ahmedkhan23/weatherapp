package com.ahmed.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationRepositoryImpl(val context: Context) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(callback: (Location) -> Unit) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        val cancellationTokenSource = CancellationTokenSource()

        val currentLocationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationProviderClient.getCurrentLocation(currentLocationRequest, cancellationTokenSource.token)
            .addOnSuccessListener {
                callback(it)
            }
            .addOnFailureListener {
                println("couldn't find location ${it.message}")
            }
    }

    override fun getLocationUpdates() {

    }
}