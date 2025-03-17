package com.ahmed.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ahmed.weatherapp.BuildConfig
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.model.WeatherResponse
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class LocationWeatherRepositoryImpl(
    private val context: Context,
    private val weatherAPI: WeatherAPI) : LocationWeatherRepository {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation() {

        val cancellationTokenSource = CancellationTokenSource()

        val currentLocationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationProviderClient.getCurrentLocation(currentLocationRequest, cancellationTokenSource.token)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                println("couldn't find location ${it.message}")
            }
    }



    override fun getLocationUpdates() {

        // TODO -
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocationWeather(): WeatherResponse? {
        val location = fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        val weather = weatherAPI.getCurrentWeather(location.latitude.toString(),
            location.longitude.toString(), BuildConfig.WEATHER_API_KEY)

        Log.d(TAG, "weather data recvd $weather")

        return weather.body()
    }

}