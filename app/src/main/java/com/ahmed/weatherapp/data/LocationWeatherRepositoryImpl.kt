package com.ahmed.weatherapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ahmed.weatherapp.BuildConfig
import com.ahmed.weatherapp.R
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
    override suspend fun getCurrentLocationWeather(): NetworkResult<WeatherResponse> {

        val location = try {
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).await()
        } catch (e: Exception) {
            Log.d(TAG, "unable to retrieve location")
            return NetworkResult.Error(context.getString(R.string.location_retrieve_error))
        }

        val weatherResponse = try {
            weatherAPI.getCurrentWeather(location.latitude.toString(),
                location.longitude.toString(), BuildConfig.WEATHER_API_KEY)
        } catch (e: Exception) {
            return NetworkResult.Error("Error ${e.message ?: " unknown"}")
        }

        Log.d(TAG, "weather data recvd $weatherResponse")

        return if (weatherResponse.isSuccessful) {
            NetworkResult.Success(weatherResponse.body()!!)
        } else {

            NetworkResult.Error(getErrorMsg(weatherResponse.code()))
        }



    }

    private fun getErrorMsg(code: Int): String {
        return when (code) {
            400 -> context.getString(R.string.bad_http_request)
            401 -> context.getString(R.string.unauthorized_check_app_key)
            403 -> context.getString(R.string.no_permissions)
            404 -> context.getString(R.string.resource_not_found)
            500 -> context.getString(R.string.internal_server_error)
            503 -> context.getString(R.string.server_unavailable)
            else -> context.getString(R.string.unknown_error) + " $code"
        }
    }

}